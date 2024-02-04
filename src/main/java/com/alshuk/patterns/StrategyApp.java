package com.alshuk.patterns;

//Паттерн Стратегия

//Этот паттерн проектирования используется, когда есть семейство схожих алгоритмов. Каждый алгоритм помещается в
// собственный класс. После чего алгоритмы поведения можно с легкостью менять друг на друга прямо из клиентского кода в
// зависимости от необходимости.
public class StrategyApp {
    public static void main(String[] args) {
        Coder coder = new Coder();
        coder.setBehavior(new Easting());
        coder.executeBehavior();

        coder.setBehavior(new Training());
        coder.executeBehavior();

        coder.setBehavior(new Coding());
        coder.executeBehavior();
    }

    static class Coder {
        private Behavior behavior;

        void setBehavior(Behavior behavior) {
            this.behavior = behavior;
        }

        void executeBehavior() {
            behavior.doIt();
        }
    }

    interface Behavior {
        void doIt();
    }

    static class Sleeping implements Behavior {
        @Override
        public void doIt() {
            System.out.println("Sleeping");
        }
    }

    static class Coding implements Behavior {
        @Override
        public void doIt() {
            System.out.println("Coding");
        }
    }

    static class Easting implements Behavior {
        @Override
        public void doIt() {
            System.out.println("Eating");
        }
    }

    static class Training implements Behavior {
        @Override
        public void doIt() {
            System.out.println("Training");
        }
    }
}
