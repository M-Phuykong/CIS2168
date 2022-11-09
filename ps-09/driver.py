import random
import time
import matplotlib as mpl
import matplotlib.pyplot as plt

from algorithm import InsertionSort, QuickSort, TimSort

NUM_ITERATION = 1000

RANDOM_MIN_NUM = -10000
RANDOM_MAX_NUM = 10001

class Counter:
    def __init__(self) -> None:
        self.compare = 0
        self.exchange = 0


def main():

    l = [random.randint(RANDOM_MIN_NUM, RANDOM_MAX_NUM) for _ in range(100)]
    
    # insertionSort = InsertionSort(l.copy(), Counter())
    quickSort = QuickSort(l.copy(), Counter())
    # timSort = TimSort(l.copy(), Counter())
    start = time.time()
    # print(insertionSort.start())
    print(quickSort.start())

    print(time.time() - start)
    # print(timSort.start())


    return True

if __name__ == "__main__":
    main()