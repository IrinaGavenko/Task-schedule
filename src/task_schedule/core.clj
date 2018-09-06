(ns task-schedule.core
  (:require [clojure.string :as cstr]
            [clj-time.core :as t]
            [clj-time.periodic :as p]
            [clj-time.local :as l])
  (:require [task-schedule.jobs :refer [task-list]]
            [task-schedule.config :refer [conf]]))

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

;;_____________________________________________________________
#_(def test-task {:task-type "task3"
                :schedule 5
                :run-at (t/now)
                :params [1 2 3]})

;; Предполагается, что периодичность задается в часах
;; НАДО ПОПРАВИТЬ!!!
(defn add-time
  "Get next time for processing"
  [schedule run-at]
  (nth (p/periodic-seq
            run-at
            (t/hours schedule)) 1))

(defn update-task
  "Add task with new processing time"
  [{:keys [task-type schedule run-at params]}]
     {:task-type task-type
     :schedule schedule
     :run-at (add-time schedule run-at)
     :params params})

#_(println (update-task test-task))

(defn launch-task
  "Find and launch processing of new task"
  [{:keys [task-type schedule run-at params] :as task}]
  (case task-type
    "task1" (task1-processing params)
    "task2" (task2-processing params)
    "task3" (task3-processing params)
    "task4" (task4-processing params))
  (update-task task))

#_(println (launch-task test-task))

(defn check-time
  "Necessity to run a task"
  [run-at]
  (t/before? run-at (t/now)))

#_(println (check-time (test-task :run-at)))

(defn next-task
  [task]
  (if (check-time (task :run-at))
    (launch-task task)))

#_(next-task test-task)

;;_____________________________________________________________
;; run application

(defn run
  [task-list]
  (Thread/sleep (config/conf :pause))
  (->>
    (map next-task task-list)
    (doall)
    (recur)))

(run jobs/task-list)