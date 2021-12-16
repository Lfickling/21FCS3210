/*
 * CS3210 - Principles of Programming Languages - Fall 2020
 * Instructor: Thyago Mota
 * Description: Activity 22 - Rocket Launch Simulation
 * go run RocketLaunch.go
 */

package main

import (
	"fmt"
	"math/rand"
	"sync"
	"time"
)

type Control struct {
	mu    sync.Mutex
	state StateType
}

type StateType string

const SCHEDULED StateType = "scheduled"
const INITIATED StateType = "initiated"
const LAUNCHED StateType = "launched"
const ABORTED StateType = "aborted"

const ABORT_PROBABILITY = 20 // 0-100%

var wg sync.WaitGroup

// TODO: run an infinite loop until state is either ABORTED or LAUNCHED
// the operator should abort the launching based on ABORT_PROBABILITY
// the operator should "sleep" for random seconds (<=10s)
// add appropriate messages at each iteration
func operator(name string, control *Control) {
	defer wg.Done()
	for {
		control.mu.Lock()
		if control.state == LAUNCHED {
			control.mu.Unlock()
			fmt.Printf("Operator %s is supes happy \n", name)
			break
		} else if control.state == ABORTED {
			control.mu.Unlock()
			fmt.Printf("Operator %s is frustrated with another aborted mission \n", name)
			break
		} else if control.state == SCHEDULED {
			control.mu.Unlock()
			fmt.Printf("Operator %s is taking a nap waiting for launch to be initiated \n", name)
			time.Sleep(time.Duration(rand.Intn(5)) * time.Second)
			fmt.Printf("Operator %s woke up!", name)
		} else {
			if rand.Intn(100) <= ABORT_PROBABILITY {
				fmt.Printf("Operator %s is trying to abort the launch \n", name)
				control.state = ABORTED
				fmt.Printf("Rocket launch aborted by Operator %s \n", name)
				control.mu.Unlock()
				break
			} else {
				control.mu.Unlock()
				fmt.Printf("Operator %s is taking a nap waiting for launch \n", name)
				time.Sleep(time.Duration(rand.Intn(10)) * time.Second)
				fmt.Printf("Operator %s woke up! \n", name)
			}
		}
	}

}

// TODO: launch a rocket by changing its state
// SCHEDULED is the initial state
// from SCHEDULED the rocket goes to (launch sequence) INITIATED
// it state is INITIATED, display a message with #sec til launch; if sec > 15s, sleep for 5s and then decrease time by 5;
// if sec <= 15s, sleep for 1s and then decrease time by 1s; if sec == 0, display a message saying that the rocket was launched
// and change the state to LAUNCHED;
// if the state is ABORTED, display a message (saying that the launch was abborted by the operator) and finish execution
func launch(sec int, control *Control) {
	defer wg.Done()
	for {
		control.mu.Lock()
		if control.state == SCHEDULED {
			control.state = INITIATED
			control.mu.Unlock()
		} else if control.state == INITIATED {
			control.mu.Unlock()
			fmt.Println("seconds until launch: ", sec)
			if sec == 0 {
				control.mu.Lock()
				control.state = LAUNCHED
				control.mu.Unlock()
				println("LIFT OFF!!! rocket has been launched")
				break
			} else if sec <= 15 {
				time.Sleep(time.Duration(rand.Intn(1)) * time.Second)
				sec -= 1
			} else if sec > 15 {
				time.Sleep(time.Duration(rand.Intn(5)) * time.Second)
				sec -= 5
			}
		} else if control.state == ABORTED {
			control.mu.Unlock()
			println("ABORT! ABORT! Launch has been aborted by the operator")
			break
		}
	}
}

// TODO: run simulations changing ABORT_PROBABILITY and number of operators
func main() {
	var control Control
	control.state = SCHEDULED

	// TODO: change it based on #operators + 1 (the rocket)
	wg.Add(3)

	// TODO: launch a rocket!
	go launch(20, &control)

	// TODO: run operator goroutines
	go operator("jane", &control)
	go operator("Lettie", &control)

	wg.Wait()
}
