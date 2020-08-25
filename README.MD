# INTRODUCTION TO PERIODS
The scope of this session is to know how to develop a rich value object (and a root aggregation) using TDD.

Let's define what is a Period.

A Period is just an interval of time. So it has a start time and a future end.
From the point of view of our example, we're going to assume we're going to work ONLY with closed Periods.


So let's imagine we're working in a system that they must prevented against conflicts in period, for example, a Scheduling System for appointments so, please develop a solution that easily help us to identify if an appointment can be added to our system or not because it collides
To make our job easier, our appointment will be sorted by the start date of every period
We can define a conflict if one period is inside in another one (the whole period or just a part of it)
Also be aware of, illegal periods (start > end) are not allowed

## Do not forget to introduce the next concepts in the exercise

* Value Objects
* Aggregates Root
* Stream and lambdas
* Unit Test without Mocks
* Big O complexity
* Live Templates in intelliJ
* Junit 4