


const getRandomInt = (max: number) => Math.floor(Math.random() * max);

function insertionSort(){

}

function quickSort(){

}

function timSort(){

}

function main(){
    var startTime = performance.now();
    console.log("hello world");
    var endTime = performance.now();

    let list: Array<Number>;

    for (let index = 0; index < 10; index++) {
        list.push(getRandomInt(20));
    }
    
    console.log(endTime - startTime);
    console.log(getRandomInt(5));
}

main()
