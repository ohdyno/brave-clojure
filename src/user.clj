(ns user)

(def asym-hobbit-body-parts [{:name "head" :size 3}
                             {:name "left-eye" :size 1}
                             {:name "left-ear" :size 1}
                             {:name "mouth" :size 1}
                             {:name "nose" :size 1}
                             {:name "neck" :size 2}
                             {:name "left-shoulder" :size 3}
                             {:name "left-upper-arm" :size 3}
                             {:name "chest" :size 10}
                             {:name "back" :size 10}
                             {:name "left-forearm" :size 3}
                             {:name "abdomen" :size 6}
                             {:name "left-kidney" :size 1}
                             {:name "left-hand" :size 2}
                             {:name "left-knee" :size 2}
                             {:name "left-thigh" :size 4}
                             {:name "left-lower-leg" :size 3}
                             {:name "left-achilles" :size 1}
                             {:name "left-foot" :size 2}])

(defn matching-part
  [part]
  {:name (clojure.string/replace (:name part) #"^left-" "right-")
   :size (:size part)})

(def hobbit-body-parts
  (->>
    (map #(set [% (matching-part %)]) asym-hobbit-body-parts)
    (reduce into [])))

(defn slow-hit
  "Hit a body part randomly, but do it in a resource-intensive way."
  [body-parts]
  (rand-nth (reduce into (map #(repeat (:size %) %) body-parts))))

(defn hit
  "Choose a body part to hit randomly, where each part's probability of being
  hit is weighted by the part's size."
  [body-parts]
  (let [total (reduce + (map :size body-parts))
        target (rand total)]
    (loop [[part & remaining] body-parts
           slice (:size part)]
      (if (> slice target)
        part
        (recur remaining (+ slice (:size (first remaining))))))))