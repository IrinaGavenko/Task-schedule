(ns task-schedule.core
  (:require [clojure.string :as cstr]
            [clj-time.core :as t]
            [clj-time.periodic :as p]
            [clj-time.local :as l])
  (:require [task-schedule.jobs :refer [task-list]]
            [task-schedule.config :refer [conf]])
  (:require [com.stuartsierra.component :as component]))

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
  (t/plus run-at (t/hours schedule)))

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
    (launch-task task)
    task))

;;_____________________________________________________________
;; run application

(defn start-circle
  [processed? task-list]
  (when @processed?
    (Thread/sleep (:pause conf))
    (->>
      (map next-task task-list)
      (doall)
      (recur processed?))))

#_(run (atom true) task-list)

(defn run
  [processed? jobs]
  (future (start-circle processed? (:task-list jobs))))


(defrecord MainComponent
  [jobs]
  component/Lifecycle

  (start [this]
    (let [processed? true]
      (run processed? jobs)
      (assoc this :processed? processed?)))

  (stop [this]
    (println this)
    (update this :processed? #(reset! % false))
    )
  )

#_(defn waste-time
  [a]
  (Thread/sleep 10000)
  a)

(->
  (component/system-map
   :main-component (map->MainComponent {:jobs task-list}))
  (component/start)
  ;;(waste-time)
  ;;(component/stop)
  )

#_(next-task test-task)