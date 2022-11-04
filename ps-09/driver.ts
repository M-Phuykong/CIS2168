


const getRandomInt = (max: number) => Math.floor(Math.random() * max);

function insertionSort(list: Array<number>): Array<number> {
    let i: number = 1;

    while (i < list.length) {
        let j: number = i;

        while (j > 0 && list[j - 1] > list[j]) {
            let tmp: number = list[j - 1];
            list[j - 1] = list[j];
            list[j] = tmp;
            j--;
        }
        
        i++;
    }

    return list;
}

function quickSort(){

}

function timSort(){

}

function main(){
    var startTime = performance.now();
    console.log("hello world");
    var endTime = performance.now();

    let list: Array<number> = [];

    for (let index = 0; index < 10; index++) {
        list.push(getRandomInt(20));
    }

    console.log(list.toString())
    console.log(insertionSort(list.slice()).toString());
    console.log(list.toString())
    
}

main()
