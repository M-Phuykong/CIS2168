import random

RANDOM_MIN_NUM = -100
RANDOM_MAX_NUM = 101

class Counter:
    def __init__(self) -> None:
        self.compare = 0
        self.exchange = 0

# https://en.wikipedia.org/wiki/Insertion_sort
#
def insertionSort(list, counter: Counter):
    
    i = 1
    while i < len(list):
        j = i
        while j > 0 and list[j -1] > list[j]:
            counter.compare += 1
            
            list[j], list[j - 1] = list[j - 1], list[j]
            counter.exchange += 1

            j -= 1
        i += 1
    
    return counter

# https://en.wikipedia.org/wiki/Quicksort
#
def quickSort(list, low, high, counter: Counter):

    if low >= high or low < 0:
        return
    
    def partition(list, low , high):
        pivot = list[high]

        i = low - 1

        for j in range(low, high):

            if list[j] <= pivot:
                counter.compare += 1

                i += 1

                list[i], list[j] = list[j], list[i]
                counter.exchange += 1

        i += 1

        list[i], list[high] = list[high], list[i]
        counter.exchange += 1

        return i

    p = partition(list, low, high)

    quickSort(list, low, p - 1, counter)
    quickSort(list, p + 1, high, counter)

    print(counter.compare, counter.exchange)

    return list


def timSort():
    pass

def main():

    l = [random.randint(RANDOM_MIN_NUM, RANDOM_MAX_NUM) for _ in range(10)]

    # print(l)
    # print(insertionSort(l.copy(), Counter()))
    print(quickSort(l.copy(), 0, len(l) - 1, Counter()))
    # print(l)


    pass

if __name__ == "__main__":
    main()