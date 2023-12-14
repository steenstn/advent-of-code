package year2023

import Coordinate
import Day
import Utils.Companion.indicesOf

class Day14 : Day() {

    fun part1() {
        val rocks = mutableListOf<Coordinate>()
        val cubeRocks = mutableListOf<Coordinate>()

        list.forEachIndexed { index, line ->
            val rocksOnLine = line.indicesOf('O').map { Coordinate(it, index) }
            val cubeRocksOnLine = line.indicesOf('#').map { Coordinate(it, index) }
            rocks.addAll(rocksOnLine)
            cubeRocks.addAll(cubeRocksOnLine)
        }
        for (y in list.indices) {
            for (x in list.first().indices) {
                if(rocks.contains(Coordinate(x,y))) {
                    print("O")
                } else if(cubeRocks.contains(Coordinate(x,y))) {
                    print("#")
                } else {
                    print(".")
                }
            }
            println()
        }
        do {
            var movement = false
            rocks.forEach { rock ->
                if (rock.y > 0 && !rocks.contains(Coordinate(rock.x, rock.y-1)) && !cubeRocks.contains(Coordinate(rock.x, rock.y-1))) {
                    rock.y--
                    movement = true
                }
            }

        } while (movement)

        println(rocks.sumOf { list.size - it.y })
    }
}