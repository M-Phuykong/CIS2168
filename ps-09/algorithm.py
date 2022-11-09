TIM_SORT_MIN_RUN_LENGTH = 32

class InsertionSort():
    def __init__(self, list, counter) -> None:
        self.list = list
        self.counter = counter
    
    # https://en.wikipedia.org/wiki/Insertion_sort
    #
    def start(self):
        
        i = 1
        while i < len(self.list):
            j = i
            while j > 0 and self.list[j -1] > self.list[j]:
                self.counter.compare += 1
                
                self.list[j], self.list[j - 1] = self.list[j - 1], self.list[j]
                self.counter.exchange += 1

                j -= 1
            i += 1
        
        return self.list, self.counter

class QuickSort():

    def __init__(self, list, counter) -> None:
        self.list = list
        self.counter = counter

    # https://en.wikipedia.org/wiki/Quicksort
    #
    def partition(self, low , high):
        pivot = self.list[high]

        i = low - 1

        for j in range(low, high):

            if self.list[j] <= pivot:
                self.counter.compare += 1

                i += 1

                self.list[i], self.list[j] = self.list[j], self.list[i]
                self.counter.exchange += 1

        i += 1

        self.list[i], self.list[high] = self.list[high], self.list[i]
        self.counter.exchange += 1

        return i
    
    def quickSort(self, low, high):

        if low >= high or low < 0:
            return

        p = self.partition(low, high)

        self.quickSort(low, p - 1)
        self.quickSort(p + 1, high)

        return self.list, self.counter
    
    def start(self):
        return self.quickSort(0, len(self.list) - 1)

class TimSort():

    def __init__(self, list, counter) -> None:
        self.list = list
        self.counter = counter

    # https://www.pythonpool.com/python-timsort/
    #
    def find_minrun(self, n): 
        r = 0
        while n >= TIM_SORT_MIN_RUN_LENGTH: 
            r |= n & 1
            n >>= 1 # divide by 2
        return n + r

    def insertion_sort(self, left, right): 
        for i in range(left+1,right+1):
            element = self.list[i]
            j = i-1
            while element < self.list[j] and j >= left :
                self.list[j+1] = self.list[j]
                j -= 1
            self.list[j+1] = element
        return self.list

    def merge(self, l, m, r): 

        array_length1= m - l + 1
        array_length2 = r - m 

        left = []
        right = []

        for i in range(0, array_length1): 
            left.append(self.list[l + i])

        for i in range(0, array_length2): 
            right.append(self.list[m + 1 + i]) 
        i=0
        j=0
        k=l

        while j < array_length2 and  i < array_length1: 
            if left[i] <= right[j]: 
                self.list[k] = left[i] 
                i += 1

            else: 
                self.list[k] = right[j] 
                j += 1
                
            k += 1

        while i < array_length1: 
            self.list[k] = left[i] 
            k += 1
            i += 1

        while j < array_length2: 
            self.list[k] = right[j] 
            k += 1
            j += 1

    def start(self):
        n = len(self.list) 
        minrun = self.find_minrun(n) 
    
        for start in range(0, n, minrun): 
            end = min(start + minrun - 1, n - 1) 
            self.insertion_sort(start, end) 
    
        size = minrun 
        while size < n: 
    
            for left in range(0, n, 2 * size): 
    
                mid = min(n - 1, left + size - 1) 
                right = min((left + 2 * size - 1), (n - 1)) 
                self.merge(left, mid, right) 
    
            size *= 2 

        return self.list, self.counter