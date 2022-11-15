import random
import time
import matplotlib.pyplot as plt

from textwrap import wrap
from algorithm import InsertionSort, QuickSort, TimSort

NUM_ITERATION = 100

RANDOM_MIN_NUM = -10000
RANDOM_MAX_NUM = 10001

def gen_graph(x, y, y_label, title, x_label_axis, y_label_axis, out_name):
    """ 
    x: data points for the x axis
    y: a list of data points to plot (make sure they match with the y-label
                                      argument if there's multiple data points)
    y-label: list of label for each data set (if plotting multiples line, make sure the 
                                    the ordering matches the "x" argument)
    title: graph title
    x_label_axis: label for the x-axis
    y_label_axis: label for the y-axis
    out_name: output file name that the graph will be save to
    """
    plt.xkcd()
    fig, ax = plt.subplots()

    for y_val, y_lbl in zip(y,y_label):
        ax.plot(x,y_val,label = y_lbl)

    ax.set_xlabel(x_label_axis)
    ax.set_ylabel(y_label_axis)
    ax.axes.autoscale()
    ax.set_yscale('log')

    ax.set_title("\n".join(wrap(title, 40)))
    ax.legend()

    plt.savefig(out_name)


class DataSaver:
    def __init__(self) -> None:
        self.time = []
        self.exchange = []
        self.compare = []
        self.cur_time = 0
        self.cur_exchange = 0
        self.cur_compare = 0
    
    def calc_average(self):
        self.time.append(self.cur_time / NUM_ITERATION)
        self.exchange.append(self.cur_exchange / NUM_ITERATION)
        self.compare.append(self.cur_compare / NUM_ITERATION)
        self.cur_time = 0
        self.cur_exchange = 0
        self.cur_compare = 0


class Counter:
    def __init__(self) -> None:
        self.compare = 0
        self.exchange = 0

def main():

    insertSort_DS = DataSaver()
    quickSort_DS = DataSaver()
    timSort_DS = DataSaver()
    start, end = 0, 0
    r = [2 ** i for i in range(1,12)]

    for n in r:

        for _ in range(NUM_ITERATION):

            l = [random.randint(RANDOM_MIN_NUM, RANDOM_MAX_NUM) for _ in range(n)]
            
            ## InsertionSort ##
            start = time.time() * 1000 # convert to millisecond
            insertionSort = InsertionSort(l.copy(), Counter()).start()
            end = time.time() * 1000 # convert to millisecond
            insertSort_DS.cur_time += (end - start)
            insertSort_DS.cur_exchange += insertionSort[1].exchange
            insertSort_DS.cur_compare += insertionSort[1].compare

            ## QuickSort ##
            start = time.time() * 1000
            quickSort = QuickSort(l.copy(), Counter()).start()
            end = time.time() * 1000
            quickSort_DS.cur_time += (end - start)
            quickSort_DS.cur_exchange += quickSort[1].exchange
            quickSort_DS.cur_compare += quickSort[1].compare

            ## TimSort ##
            start = time.time() * 1000
            timSort = TimSort(l.copy(), Counter()).start()
            end = time.time() * 1000
            timSort_DS.cur_time += (end - start)
            timSort_DS.cur_exchange += timSort[1].exchange
            timSort_DS.cur_compare += timSort[1].compare

        insertSort_DS.calc_average()
        quickSort_DS.calc_average()
        timSort_DS.calc_average()


    gen_graph(r, 
    [insertSort_DS.time, quickSort_DS.time, timSort_DS.time], 
    ["Insertion Sort", "Quick Sort", "Tim Sort"],
    "Performance of Sorting Algorithm (Averages per 100 iteration)",
    "Sample Size (N)",
    "Time (ms) Log Scale",
    "time_comparison.png")

    gen_graph(r, 
    [insertSort_DS.exchange, quickSort_DS.exchange, timSort_DS.exchange], 
    ["Insertion Sort", "Quick Sort", "Tim Sort"],
    "Performance of Sorting Algorithm (Averages per 100 iteration)",
    "Sample Size (N)",
    "Exchange (Log Scale)",
    "exchange_comparison.png")

    gen_graph(r, 
    [insertSort_DS.compare, quickSort_DS.compare, timSort_DS.compare], 
    ["Insertion Sort", "Quick Sort", "Tim Sort"],
    "Performance of Sorting Algorithm (Averages per 100 iteration)",
    "Sample Size (N)",
    "Compare (Log Scale)",
    "compare_comparison.png")

    return True

if __name__ == "__main__":
    main()
