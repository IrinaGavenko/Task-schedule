(ns task-schedule.core)

(def task-list [{:task-type "task1"
                 :schedule 5000
                 :params [1]}
                {:task-type "task2"
                 :schedule 10000
                 :params [2 3]}
                {:task-type "task3"
                 :schedule 12000
                 :params [4 5 6]}
                {:task-type "task4"
                 :schedule 15000
                 :params [7 8 9 10]}])

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

#_(def task {:task-type "hello3"
           :schedule 5000
           :params [1 2 3]})

(defn new-task
  "Find and launch processing of new task"
  [task]
  (case (task :task-type)
    "task1" (task1-processing (task :params))
    "task2" (task2-processing (task :params))
    "task3" (task3-processing (task :params))
    "task4" (task4-processing (task :params)))
  (Thread/sleep (task :schedule)))

#_(next-task task)

(defn run
  "Main function -- run application"
  [task-list]
  (run! (fn [task'] (new-task task')) task-list))

(run task-list)