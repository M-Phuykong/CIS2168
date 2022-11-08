const RAND_UPPER_LIMIT = 50;


const getRandomInt = (max: number) => Math.floor(Math.random() * max);

const swap = (arr: Array<number>, i: number, j:number) => { let temp: number = arr[i]; arr[i] = arr[j]; arr[j] = temp; }

function insertionSort(list: Array<number>): Array<number> {
    let i: number = 1;

    while (i < list.length) {
        let j: number = i;

        while (j > 0 && list[j - 1] > list[j]) {
            swap(list, j - 1, j)
            j--;
        }
        i++;
    }
    return list;
}

function quickSort(list: Array<number>, low: number, high: number){
    
    if (low >= high){
        return;
    }
    
    function partition(list: Array<number>, low: number, high: number): number{
        
        let pivot: number =  list[high];
    
        let index: number = low;
    
        for (let j = index; j < high; j++) {
            
            if (list[j] < pivot){
                swap(list, index, j);
                index ++;
            }
        }
    
        swap(list, index, high);
        
        return index;
    }

    let pi: number = partition(list, low, high);
    
    quickSort(list, low, pi - 1);
    quickSort(list, pi + 1, high);

    return list;
}


function timSort(){
    
}

function main(){
    var startTime = performance.now();
    console.log("hello world");
    var endTime = performance.now();

    let list: Array<number> = [];

    for (let index = 0; index < 10; index++) {
        list.push(getRandomInt(RAND_UPPER_LIMIT));
    }

    console.log(list.toString())
    console.log(insertionSort(list.slice()).toString());
    console.log(quickSort(list.slice(), 0, list.length - 1)?.toString());
    
}

main()
