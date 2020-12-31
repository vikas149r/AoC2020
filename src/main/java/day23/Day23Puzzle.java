package day23;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Day23Puzzle {

    private static class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }

        @Override
        public String toString() {
            return item.toString();
        }
    }

    public static void main(String[] args) throws Exception {
        final String input = "476138259";

        Map<Integer, Node> numberToLocation = new LinkedHashMap<>();
        Node<Integer> prevNode = null;
        Node<Integer> firstNode = null;
        Node<Integer> lastNode = null;

        for (Character character : input.toCharArray()) {
            Integer number = Character.digit(character, 10);
            Node<Integer> node = new Node(prevNode, number, null);

            if (firstNode == null) {
                firstNode = node;
            }

            if (prevNode != null) {
                prevNode.next = node;
            }

            prevNode = node;
            numberToLocation.put(number, node);
        }

        int largestNumber = 1000000;
        for (int i = 10; i <= largestNumber; i++) {
            Node node = new Node(prevNode, i, null);

            if (prevNode != null) {
                prevNode.next = node;
            }

            prevNode = node;
            numberToLocation.put(i, node);
        }

        lastNode = prevNode;

        System.out.println(numberToLocation.size());
        int count = 1;

        while (count <= 10000000) {
            Integer value = firstNode.item;
            Node<Integer> node = numberToLocation.get(value);

            List<Integer> subList = new ArrayList<>();

            Node<Integer> firstNodeOfSubList = node.next;
            node.next = null;
            firstNodeOfSubList.prev = null;
            subList.add(firstNodeOfSubList.item);
            subList.add(firstNodeOfSubList.next.item);

            Node<Integer> lastNodeOfSubList = firstNodeOfSubList.next.next;
            firstNode = lastNodeOfSubList.next;
            firstNode.prev = null;
            lastNodeOfSubList.next = null;
            subList.add(lastNodeOfSubList.item);

            int numberToSearch = value - 1;
            Node nodeToRetrieve = null;

            while (nodeToRetrieve == null) {
                if (numberToSearch == 0) {
                    numberToSearch = largestNumber;
                }

                if (subList.contains(numberToSearch)) {
                    numberToSearch--;
                    continue;
                }

                nodeToRetrieve = numberToLocation.get(numberToSearch--);
            }

            Node nextNodeOfTarget = nodeToRetrieve.next;
            firstNodeOfSubList.prev = nodeToRetrieve;
            nodeToRetrieve.next = firstNodeOfSubList;

            if (nextNodeOfTarget != null) {
                lastNodeOfSubList.next = nextNodeOfTarget;
                nextNodeOfTarget.prev = lastNodeOfSubList;
            } else {
                lastNode = lastNodeOfSubList;
            }

            node.prev = lastNode;
            lastNode.next = node;
            lastNode = node;

            count++;
        }

        Node<Integer> oneNode = numberToLocation.get(1);
        Node<Integer> nextNode = oneNode;

        /*while (nextNode.next != null) {
            nextNode = nextNode.next;
            System.out.print(nextNode.item + " ");
        }*/

        nextNode = oneNode.next;
        Node<Integer> nextToNextNode = nextNode.next;

        System.out.println(nextNode.item);
        System.out.println(nextToNextNode.item);

        long number = (long) nextNode.item * (long) nextToNextNode.item;
        System.out.println(number);
    }
}
