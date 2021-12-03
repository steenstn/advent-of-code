class Day3 :Day("day3.txt") {

    fun part1() {
        val histogram = mutableListOf(0,0,0,0,0,0,0,0,0,0,0,0)
        list.forEach { number ->
            for (x in 0..11) {
                histogram[x]+=Integer.parseInt(number[x].toString())
            }
        }
        var gamma = ""
        var epsilon =  ""
        for(x in 0..11) {
            if(histogram[x] > list.size/2) {
                gamma+=  "1"
                epsilon+=0
            } else {
                gamma+= 0
                epsilon+=1
            }
        }
        val res = Integer.parseInt(gamma, 2)*Integer.parseInt(epsilon,2)
        println(res)
    }

    fun part2() {


        val binaryList = list.map{ Integer.parseInt(it, 2) }
        val start = 0b100000000000
        var oxygen = 0
        var co2 = 0
        var listToUse = binaryList.toList()

        for(x in 0..11) {
            val index = start shr x

            val ones = listToUse.filter { it and index == index }
            val zeros = listToUse.filter { it and index == 0 }
            listToUse = if(ones.size >= zeros.size) ones.toList() else zeros.toList()

            if(listToUse.size == 1) {
                oxygen = listToUse.first()
                break
            }
        }

        listToUse = binaryList.toList()
        for(x in 0..11) {
            val index = start shr x

            val ones = listToUse.filter { it and index == index }
            val zeros = listToUse.filter { it and index == 0 }
            listToUse = if(zeros.size <= ones.size) zeros.toList() else ones.toList()
            if(listToUse.size == 1) {
                co2 = listToUse.first()
                break
            }
        }

        println(oxygen*co2)
    }


}