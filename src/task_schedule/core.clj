(ns task-schedule.core
  (:require [clojure.string :as cstr]))

;; Add parameters of new task

(def task-list [{:task-type "task1"
                 :schedule 5
                 :params [1]}
                {:task-type "task2"
                 :schedule 10
                 :params [2 3]}
                {:task-type "task3"
                 :schedule 12
                 :params [4 5 6]}
                {:task-type "task4"
                 :schedule 15
                 :params [7 8 9 10]}])

;; Add execution function of new task

(defn task1-processing
  "Processing of task1"
  [values]
  (println (str "task-1: " (+ 1 (reduce + values)))))

(defn task2-processing
  "Processing of task2"
  [values]
  (println (str "task-2: " (+ 2 (reduce + values)))))

(defn task3-processing
  "Processing of task3"
  [values]
  (println (str "task-3: " (+ 3 (reduce + values)))))

(defn task4-processing
  "Processing of task4"
  [values]
  (println (str "task-4: " (+ 4 (reduce + values)))))

(def test-task {:task-type "task3"
           :schedule 5000
           :params [1 2 3]})


#_(new-task task)

#_(defn run
  "Main function -- run application"
  [task-list]
  (run! new-task task-list))

(def curr-time 13)


;; Есть ошибка в update!!!
(defn launch-task
  "Find and launch processing of new task"
  [{:keys [task-type schedule params] :as task}]
  (case task-type
    "task1" (task1-processing params)
    "task2" (task2-processing params)
    "task3" (task3-processing params)
    "task4" (task4-processing params))
  (update task :schedule inc))

(launch-task test-task)

(defn __curr-time
  []
  (cstr/split (.toString (java.util.Date.)) #" "))

(def curr-time 13)

;; schedule format:
;; m h dom mon dow

(defn check-time
  "Necessity to run a task"
  [schedule]
  (if (<= schedule curr-time)
    true
    false))

(defn next-task
  [task]
  (if (check-time (task :schedule))
    (launch-task task)))

;; run application

#_(defn run
  [task-list]
  (run! next-task task-list)
  (recur task-list'))

#_(run task-list)