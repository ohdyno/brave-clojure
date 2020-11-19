(ns user)

(def asym-hobbit-body-parts [{:name "head" :size 3}
                             {:name "upper-left-eye" :size 1}
                             {:name "upper-left-ear" :size 1}
                             {:name "mouth" :size 1}
                             {:name "nose" :size 1}
                             {:name "neck" :size 2}
                             {:name "upper-left-shoulder" :size 3}
                             {:name "upper-left-upper-arm" :size 3}
                             {:name "chest" :size 10}
                             {:name "back" :size 10}
                             {:name "upper-left-forearm" :size 3}
                             {:name "abdomen" :size 6}
                             {:name "upper-left-kidney" :size 1}
                             {:name "upper-left-hand" :size 2}
                             {:name "upper-left-knee" :size 2}
                             {:name "upper-left-thigh" :size 4}
                             {:name "upper-left-lower-leg" :size 3}
                             {:name "upper-left-achilles" :size 1}
                             {:name "upper-left-foot" :size 2}])

(def radial-symmetry-directions
  ["upper-left", "top", "upper-right", "lower-right", "lower-left"])

(defn matching-parts
  [part]
  (for [direction ["upper-left", "top", "upper-right", "lower-right", "lower-left"]]
    {:name (clojure.string/replace (:name part) #"^upper-left" direction)
     :size (:size part)}))

(defn matching-part
  [part]
  {:name (clojure.string/replace (:name part) #"^left-" "right-")
   :size (:size part)})

(def hobbit-body-parts
  (->>
    (map #(set (conj (matching-parts %) %)) asym-hobbit-body-parts)
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

(defn dec-maker
  "Takes in a number n and create a function that would decrement its input by n."
  [amount]
  (fn [x] (- x amount)))