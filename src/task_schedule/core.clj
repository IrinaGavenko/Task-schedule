(ns task-schedule.core
  (:require [clojure.string :as cstr]))

;; Add parameters of new task

(def task-list [{:task-type "task1"
                 :schedule 5
                 ;; :run-at (curr-time)
                 :params [1]}
                {:task-type "task2"
                 :schedule 10
                 ;; :run-at (curr-time)
                 :params [2 3]}
                {:task-type "task3"
                 :schedule 12
                 ;;:run-at (curr-time)
                 :params [4 5 6]}
                {:task-type "task4"
                 :schedule 15
                 ;; :run-at (curr-time)
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


;; Костыльный способ конвертации в нужный формат
#_(defn convert-week
  [week]
  (case week
    "Mon" 1
    "Tue" 2
    "Wen" 3
    "Thu" 4
    "Fri" 5
    "Sut" 6
    "Sun" 7))

#_(defn convert-month
  [month]
  (case month
    "Jan" 1
    "Feb" 2
    "Mar" 3
    "Apr" 4
    "May" 5
    "Jun" 6
    "Jul" 7
    "Aug" 8
    "Sep" 9
    "Oct" 10
    "Nov" 11
    "Dec" 12))

#_(defn curr-time
  []
  (let [[week month date time city year]
        (cstr/split (.toString (java.util.Date.)) #" ")]
    (let [[hours mins secs] (cstr/split time #":")]
      {:m   (Integer/parseInt mins)
       :h   (Integer/parseInt hours)
       :dom (convert-week week)
       :mon (convert-month month)
       :dow (Integer/parseInt year)})))


#_(curr-time)

(def curr-time 13)

(defn check-time
  "Necessity to run a task"
  [schedule]
  (<= schedule curr-time))

(defn next-task
  [task]
  (if (check-time (task :schedule))
    (launch-task task)))

;; run application

(defn run
  [task-list]
  (Thread/sleep 5000)
  (->>
    (map next-task task-list)
    (doall)
    (recur)))

(run task-list)