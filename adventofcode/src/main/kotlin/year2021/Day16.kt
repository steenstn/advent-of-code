package year2021

import Day

var versionNumbers = 0L

class Day16 : Day() {

    val binaryMap = mapOf(
        '0' to "0000",
        '1' to "0001",
        '2' to "0010",
        '3' to "0011",
        '4' to "0100",
        '5' to "0101",
        '6' to "0110",
        '7' to "0111",
        '8' to "1000",
        '9' to "1001",
        'A' to "1010",
        'B' to "1011",
        'C' to "1100",
        'D' to "1101",
        'E' to "1110",
        'F' to "1111",
    )
    var binaryString = ""

    init {
        list.forEach { line ->
            line.forEach { c ->
                binaryString += binaryMap[c]
            }
        }
        println(binaryString)

    }

    fun part1() {

        val signal = Signal(binaryString)
        val signalReader = SignalReader()

        signalReader.readSignal(signal)
        println(versionNumbers)

    }


    fun part2() {

        val signal = Signal(binaryString)
        val signalReader = SignalReader()

        signalReader.readSignal(signal)
        println(signalReader.operatorTree)
        println(signalReader.operatorTree!!.getValue())

    }


    class SignalReader {
        var operatorTree: Packet? = null

        fun readSignal(signal: Signal) {

            while (!signal.isEmpty() && signal.string.any { it == '1' }) {

                val header = readHeader(signal)

                if (header.type == Type.Literal) {
                    operatorTree = Packet(Type.Literal, readLiteralValue(signal))

                } else {
                    operatorTree = Packet(header.type)
                    readOperator(signal, operatorTree!!)

                }
            }

        }

        private fun readOperator(signal: Signal, node: Packet) {
            val lengthTypeId = signal.pop(1)

            if (lengthTypeId == 0L) {

                val totalLength = signal.pop(15)


                val subSignal = signal.getPartOfSignal(totalLength)
                signal.drop(totalLength.toInt())

                while (subSignal.length() > 0) {
                    val header = readHeader(subSignal)
                    if (header.type == Type.Literal) {

                        node.addChild(Packet(Type.Literal, readLiteralValue(subSignal)))


                    } else {
                        val child = node.addChild(Packet(header.type))
                        readOperator(subSignal, child)
                    }
                }


            } else if (lengthTypeId == 1L) {
                val numSubpackets = signal.pop(11)

                for (i in 0 until numSubpackets) {

                    val header = readHeader(signal)
                    if (header.type == Type.Literal) {
                        node.addChild(Packet(Type.Literal, readLiteralValue(signal)))


                    } else {
                        val child = node.addChild(Packet(header.type))
                        readOperator(signal, child)
                    }

                }
            } else {
                throw Exception("Invalid length type id")
            }

        }

        private fun readHeader(signal: Signal): Header {
            val packetVersion = signal.pop(3)
            versionNumbers += packetVersion
            val packetTypeId = signal.pop(3)
            val header = Header.createHeader(packetVersion, packetTypeId.toInt())
            return header
        }

        private fun readLiteralValue(signal: Signal): Long {
            var hasMoreBits = true
            var valueString = ""
            while (hasMoreBits) {
                val result = signal.pop(5)
                valueString += (result and 0b01111).toInt().toBinary(4)
                hasMoreBits = (result and 0b10000) > 0

            }
            val value = valueString.toLong(2)

            return value
        }
    }

    data class Header(val version: Long, val type: Type) {
        companion object {
            fun createHeader(version: Long, type: Int): Header {
                val resultingType: Type = when (type) {
                    0 -> Type.Sum
                    1 -> Type.Product
                    2 -> Type.Min
                    3 -> Type.Max
                    4 -> Type.Literal
                    5 -> Type.GreaterThan
                    6 -> Type.LessThan
                    7 -> Type.Equal
                    else -> throw Exception()
                }
                return Header(version, resultingType)

            }
        }
    }

    enum class Type {
        Literal, Sum, Product, Min, Max, GreaterThan, LessThan, Equal
    }

    class Packet(private val type: Type, private var value: Long = 0, var parent: Packet? = null, private val children: MutableList<Packet> = mutableListOf()) {

        fun addChild(child: Packet): Packet {
            this.children.add(child)
            child.parent = this
            return child
        }

        fun getValue(): Long {
            return when (type) {
                Type.Literal -> value
                Type.Sum -> children.sumOf { it.getValue() }
                Type.Product -> {
                    if (children.size == 1) children.first().getValue() else children.map { it.getValue() }.reduce { acc, l -> acc * l }
                }
                Type.Min -> children.minOf { it.getValue() }
                Type.Max -> children.maxOf { it.getValue() }
                Type.GreaterThan -> {
                    if (children.size != 2) throw Exception()
                    if (children.first().getValue() > children.last().getValue()) 1 else 0
                }
                Type.LessThan -> {
                    if (children.size != 2) throw Exception()
                    if (children.first().getValue() < children.last().getValue()) 1 else 0
                }
                Type.Equal -> {
                    if (children.size != 2) throw Exception()
                    if (children.first().getValue() == children.last().getValue()) 1 else 0
                }
            }.also { println("$type $it") }
        }

        override fun toString(): String {
            var s = if (this.type == Type.Literal) "" + this.value else (this.type.name)
            if (children.isNotEmpty()) {
                s += " {" + children.map { it.toString() } + " }"
            }
            return s
        }
    }

    class Signal(var string: String) {
        fun pop(numBits: Int): Long {
            val res = string.take(numBits)
            string = string.drop(numBits)
            return res.toLong(2)
        }

        fun drop(numBits: Int) {
            string = string.drop(numBits)
        }

        fun length(): Int = string.length

        fun isEmpty(): Boolean = string.isEmpty()

        fun getPartOfSignal(length: Long): Signal {
            return Signal(string.substring(0, length.toInt()))
        }

        override fun toString(): String {
            return string
        }
    }
}


fun Int.toBinary(len: Int): String {
    return String.format("%" + len + "s", this.toString(2)).replace(" ".toRegex(), "0")
}