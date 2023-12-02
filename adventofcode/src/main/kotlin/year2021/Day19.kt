package year2021

import Day
import year2021.Day19.Matrix
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.round
import kotlin.math.sin

class Day19 : Day() {

    val scanners = mutableListOf<Scanner>()

    val allRotations: Set<Matrix>

    init {
        var index = 0
        list.forEach { line ->
            if (line.contains("scanner")) {
                scanners.add(Scanner(index++, 0, 0, 0))
            } else if (line.isNotEmpty()) {
                val (xPos, yPos, zPos) = line.split(",")
                scanners.last().beacons.add(Position(xPos.toInt(), yPos.toInt(), zPos.toInt()))

            }
        }
        scanners.forEach { scanner ->
            scanner.calculateVectors()
        }


        allRotations = calculateRotations()

    }

    fun part1() {
        for (i in 0 until scanners.size - 1) {
            for (j in i + 1 until scanners.size) {
                val firstScanner = scanners[i]
                val secondScanner = scanners[j]
                val res = firstScanner.getMatches(secondScanner.getAllVectors(), allRotations)
                println("res: " + res.first)
                if (res.first != 132) {
                    continue
                }
                println("scanner $i matches scanner $j")
                val positions = secondScanner.findMatchingPositions(firstScanner.beacons, res.second)!!
                val translation = positions.first - res.second * positions.second

                secondScanner.translationToParent = Translation(res.second, translation)
                firstScanner.addChild(secondScanner)

                println("scanner $i")
                firstScanner.beacons.forEach { println(Identity() * it) }
                println("scanner $j")
                secondScanner.beacons.forEach { println(res.second * it + translation) }
                println()
                println("overlapping for scanner $i and $j")
                firstScanner.beacons.map { Identity() * it }.intersect(secondScanner.beacons.map { res.second * it + translation }).forEach { println(it) }
                println()
            }
        }


        val translated = scanners[1].translateToTopParent(scanners[1].beacons)
        println("lol")
        translated.forEach { println(it) }


        scanners.forEach { scanner ->
            print("scanner ${scanner.id} has children ")
            scanner.children.forEach {
                print(""+it.id + " ")
            }
            println()
        }
        scanners.forEach { scanner ->
            print("scanner ${scanner.id} has parent ${scanner.parent?.id} ")

            println()
        }
/*
        for (i in 0 until scanners.size - 1) {
            for (j in i + 1 until scanners.size) {
        println("$i and $j")

                scanners[0].beacons.map { year2021.Identity() * it }
                    .intersect(scanners[i].beacons.map {  it + scanners[j].translationToParent.translationVector })
                    .forEach { println(it) }
            }
        }*/

    }


    fun calculateRotations(): Set<Matrix> {
        val res = mutableListOf<Matrix>()
        var x = 0.0
        val increment = PI / 2
        while (x < 2 * PI) {

            var y = 0.0
            while (y < 2 * PI) {

                var z = 0.0
                while (z < 2 * PI) {
                    res.add(Rx(x) * Ry(y) * Rz(z))
                    z += increment
                }
                y += increment
            }
            x += increment
        }
        return res.toSet()
    }


    fun part2() {

    }

    data class Translation(val rotationMatrix: Matrix, val translationVector: Vector)

    class Scanner(val id: Int, var x: Int, var y: Int, val z: Int, val beacons: MutableList<Position> = mutableListOf()) {
        var parent: Scanner? = null
        val children = mutableListOf<Scanner>()

        var translationToParent: Translation = Translation(Identity(), Vector(0, 0, 0))

        fun addChild(scanner: Scanner) {
            this.children.add(scanner)
            scanner.parent = this
        }

        fun translateToTopParent(positions: List<Position>): List<Position> {
            var p = this
            var currentPositions = positions
            while (p.parent != null) {
                println("translating ${p.id} to ${p.parent!!.id}")
                currentPositions = currentPositions.map { (p.translationToParent.rotationMatrix * it).plus(p.translationToParent.translationVector) }
                p = p.parent!!
            }
            return currentPositions
        }


        fun calculateVectors() {
            beacons.forEach { it.vectors.clear() }

            for (i in beacons.indices) {
                for (j in beacons.indices) {
                    if (i == j) {
                        continue
                    }
                    beacons[i].vectors.add(Vector(beacons[j].x - beacons[i].x, beacons[j].y - beacons[i].y, beacons[j].z - beacons[i].z))
                }
            }
        }


        fun rotatePositions(matrix: Matrix) {
            beacons.replaceAll { matrix * it }
            calculateVectors()
        }

        fun getAllVectors(): List<Vector> = beacons.map { it.vectors }.flatten()


        fun findPositionWithVectors(vectors: List<Vector>, matrix: Matrix): Position? {
            return beacons.firstOrNull { position ->
                position.vectors.map { matrix * it }.contains(vectors.first())
            }
        }

        fun findMatchingPositions(targets: List<Position>, matrix: Matrix): Pair<Position, Position>? {
            for (target in targets) {
                val foundPosition = beacons.firstOrNull { it.vectors.map { matrix * it }.any { target.vectors.contains(it) } }
                if (foundPosition != null) {
                    return target to foundPosition
                }
            }
            return null
        }


        fun getMatches(otherVectors: List<Vector>, rotations: Set<Matrix>): Pair<Int, Matrix> {
            val result = mutableListOf<Pair<Int, Matrix>>()
            rotations.forEach { rotation ->
                val rotatedVectors = otherVectors.map { vector -> rotation * vector }

                val res = getAllVectors().intersect(rotatedVectors).size
                result.add(res to rotation)
            }
            return result.maxByOrNull { it.first }!!
        }


    }

    data class Position(val x: Int, val y: Int, val z: Int, val vectors: MutableList<Vector> = mutableListOf()) {

        fun matchesPosition(other: Position): Boolean {
            return this.x == other.x && this.y == other.y && this.z == other.z
        }

        fun plus(other: Vector) = Position(this.x + other.x, this.y + other.y, this.z + other.z)
    }

    operator fun Position.plus(other: Vector) = Position(this.x + other.x, this.y + other.y, this.z + other.z)
    operator fun Position.plus(other: Position) = Position(this.x + other.x, this.y + other.y, this.z + other.z)
    operator fun Vector.plus(other: Vector) = Vector(this.x + other.x, this.y + other.y, this.z + other.z)

    operator fun Position.minus(other: Position) = Vector(this.x - other.x, this.y - other.y, this.z - other.z)
    operator fun Vector.minus(other: Vector) = Vector(this.x - other.x, this.y - other.y, this.z - other.z)
    operator fun Position.minus(other: Vector) = Vector(this.x - other.x, this.y - other.y, this.z - other.z)
    operator fun Vector.minus(other: Position) = Vector(this.x - other.x, this.y - other.y, this.z - other.z)


    data class Vector(val x: Int, val y: Int, val z: Int) {
/*
        override fun equals(other: Any?): Boolean {
            if (other is Vector) {
                return (this.x == other.x && this.y == other.y && this.z == other.z) ||
                        (this.x * -1 == other.x && this.y * -1 == other.y && this.z == other.z)
            }
            return false
        }*/

    }

    data class Matrix(
        val m00: Int, val m01: Int, val m02: Int,
        val m10: Int, val m11: Int, val m12: Int,
        val m20: Int, val m21: Int, val m22: Int

    ) {

        operator fun times(v: Vector) = Vector(
            this.m00 * v.x + this.m01 * v.y + this.m02 * v.z,
            this.m10 * v.x + this.m11 * v.y + this.m12 * v.z,
            this.m20 * v.x + this.m21 * v.y + this.m22 * v.z,
        )

        operator fun times(v: Position) = Position(
            this.m00 * v.x + this.m01 * v.y + this.m02 * v.z,
            this.m10 * v.x + this.m11 * v.y + this.m12 * v.z,
            this.m20 * v.x + this.m21 * v.y + this.m22 * v.z,
        )

        operator fun times(o: Matrix) = Matrix(
            this.m00 * o.m00 + this.m01 * o.m10 + this.m02 * o.m20, this.m00 * o.m01 + this.m01 * o.m11 + this.m02 * o.m21, this.m00 * o.m02 + this.m01 * o.m12 + this.m02 * o.m22,
            this.m10 * o.m00 + this.m11 * o.m10 + this.m12 * o.m20, this.m10 * o.m01 + this.m11 * o.m11 + this.m12 * o.m21, this.m10 * o.m02 + this.m11 * o.m12 + this.m12 * o.m22,
            this.m20 * o.m00 + this.m21 * o.m10 + this.m22 * o.m20, this.m20 * o.m01 + this.m21 * o.m11 + this.m22 * o.m21, this.m20 * o.m02 + this.m21 * o.m12 + this.m22 * o.m22

        )


    }
/*
[m00, m01, m02]  [m00, m01, m02]
[m10, m11, m12]  [m10, m11, m12]
[m20, m21, m22]  [m20, m21, m22]

 */

    fun Rx(degrees: Double) =
        Matrix(
            1, 0, 0,
            0, round(cos(degrees)).toInt(), round(-sin(degrees)).toInt(),
            0, round(sin(degrees)).toInt(), round(cos(degrees)).toInt()
        )

    fun Ry(degrees: Double) =
        Matrix(
            round(cos(degrees)).toInt(), 0, round(sin(degrees)).toInt(),
            0, 1, 0,
            round(-sin(degrees)).toInt(), 0, round(cos(degrees)).toInt()
        )

    fun Rz(degrees: Double) =
        Matrix(
            round(cos(degrees)).toInt(), round(-sin(degrees)).toInt(), 0,
            round(sin(degrees)).toInt(), round(cos(degrees)).toInt(), 0,
            0, 0, 1
        )


}

fun Identity() =
    Matrix(
        1, 0, 0,
        0, 1, 0,
        0, 0, 1
    )

