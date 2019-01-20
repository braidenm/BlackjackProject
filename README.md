https://github.com/braidenm/BlackjackProject## BlackJack Project

skill Distillery Week 3 Homework project
Main App is in
### Overview

## Blackjack Game:
* Create a player, players start out with $100
* Choose how big the deck is, how many other players, and the minimum bet
* rules for double down, split, and soft ace are in place
* can split as many times as available (mostly works, in some cases there are bugs)
* Players are shown and will keep playing if you bust and stay at the table
* Other players leave the table if they run out of money
* Exceptions are handled within the system
* Play until you leave the Table or run out of money

## Tech and Structure

* JUnit tests
* Object oriented (classes, enum, abstract classes, 4 pilers, etc.)
* Nested Conditional statments

## Stretch Goals

* Add option to get advice
* Fix formatting (was just getting it to work before setting the format exactly)
* various bugs still exist and need to be handled. (I have fixed all the ones I ran into, but the more I look into it and the more I find. Will be working on that and posting updates
* Have the players show different personalities


## Challenges
* Getting the soft ace implemented. I got the game working then started with the soft ace. I tried adding another enum class for Rank. after trying many different ways to implement that I changed to adding a setter in the existing Rank enum class. to find out it changes all the values for all of the aces when implemented. So I finally landed on adding another field of "ACE"  to the Rank class and in the deck made sure it didn't add the ACE value: 1 cards. then I wrote a bunch of if statements to switch the Ace Value 11 to Ace value 1.

* Money. I got money in just fine but when I wanted to implement the split card function the way I had it wasn't working. so I moved the money field to the hand class, after many bugs I made a wallet class and implemented it into the player. that didn't work, so I landed on keeping the money in the player class and having the the hand of cards class keep the amount of the bet.

* the split card with having multiple players and soft aces was the hardest. so many bugs came with implementing the split card function. the money thing was first, then the aces need to be restructured by passing through the individual hand instead of the player.getHand. needed to have several for loops that went through each hand to pass through the various filters of options. one small change would cause a waterfall effect of other small changes, and on and on.
