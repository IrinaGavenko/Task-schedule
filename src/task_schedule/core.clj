(ns task-schedule.core)

(def task-list [{:task-type "hello"
                 :schedule 5000
                 :params [1]}
                {:task-type "hello2"
                 :schedule 10000
                 :params [2 3]}
                {:task-type "hello3"
                 :schedule 12000
                 :params [4 5 6]}])

(defn hello-processing
  [values]
  (println (str "task-1: " (+ 1 (reduce + values))))
  true)

(defn hello2-processing
  [values]
  (println (str "task-2: " (+ 2 (reduce + values))))
  true)

(defn hello3-processing
  [values]
  (println (str "task-3: " (+ 3 (reduce + values))))
  true)

#_(def task {:task-type "hello3"
           :schedule 5000
           :params [1 2 3]})

(def stop-value 1)

(defn next-task
  [task]
  (case (task :task-type)
    "hello" (hello-processing (task :params))
    "hello2" (hello2-processing (task :params))
    "hello3" (hello3-processing (task :params)))
  (Thread/sleep (task :schedule)))

#_(next-task task)

(defn run-task
  [task-list]
  (while (< stop-value 2) (do
                           (run! (fn [task'] (next-task task')) task-list)
                           (def stop-value (inc stop-value)))))

(run-task task-list)