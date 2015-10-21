(ns card-game-war.game-test
  (:require [clojure.test :refer :all]
            [card-game-war.game :refer :all]))

(defn wins? [result]
  (let [[p1 p2] result]
    (if (empty? p1)
      :player-2
      :player-1)))

;; Fill in  tests for your game
(deftest test-play-round
  (testing "the highest rank wins the cards in the round"
    (is (= :player-2
           (wins? (play-round [:spade 2] [:spade :ace])))))
  (testing "queens are higher rank than jacks"
    (is (= :player-1
           (wins? (play-round [:foo :queen] [:foo :jack])))))
  (testing "kings are higher rank than queens"
    (is (= :player-2
           (wins? (play-round [:foo :queen] [:foo :king])))))
  (testing "aces are higher rank than kings"
    (is (= :player-2
           (wins? (play-round [:foo :king] [:foo :ace])))))
  (testing "if the ranks are equal, clubs beat spades"
    (is (= :player-1
           (wins? (play-round [:club 2] [:spade 2])))))
  (testing "if the ranks are equal, diamonds beat clubs"
    (is (= :player-2
           (wins? (play-round [:club :ace] [:diamond :ace])))))
  (testing "if the ranks are equal, hearts beat diamonds"
    (is (= :player-1
           (wins? (play-round [:heart :ace] [:diamond :ace]))))))

(deftest test-play-game
  (testing "the player loses when they run out of cards"
    (is (= :player-1
           (play-game [[:heart :ace]] [[:diamond :ace]])))))
