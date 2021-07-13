import random
import zlib
import sys

BITS_AMOUNT = 10000

#file = "bits.txt"


def generate(filename):
    with open(filename, "w") as file:
        for i in range(BITS_AMOUNT):
            file.write(random.randint(0, 1).__str__())


def readFromFile(filename):
    with open(filename, "r") as file:
        read = file.read().splitlines()
        file.close()
        return read[0]


def countCRC(text):
    bytesFromText = bytes(text, "utf-8")
    crcFromFile = bin(zlib.crc32(bytesFromText)).__str__()[2:].zfill(32)
    return crcFromFile


def addCRC(bytesFromFile, crcFromFile):
    conv = bytesFromFile + crcFromFile
    return conv


def insertToString(str, index, word="0"):
    return str[:index] + word + str[index:]


def convert(conv):
    check = False
    counter = 0
    i = 1
    while i < conv.__len__():
        if conv[i] == '1':
            if conv[i - 1] == '0':
                check = True
                counter = 0
            if check:
                counter += 1
                if counter == 5:
                    conv = insertToString(conv, i + 1)
                    check = False
        else:
            check = False
        i += 1
    return conv


def saveToFile(filename, conv):
    with open(filename, "w") as file:
        file.write(conv)
        file.close()


def deleteFromString(str, index):
    return str[:index] + str[index + 1:]


def deconvert(conv):
    check = False
    counter = 0
    conv=conv[8:conv.__len__()-8]
    i = 1
    while i < conv.__len__() - 8:
        if conv[i] == '1':
            if conv[i - 1] == '0':
                check = True
                counter = 0
            if check:
                counter += 1
                if counter == 5:
                    conv = deleteFromString(conv, i + 1)
                    counter = 0
        else:
            check = False
        i += 1
    return conv


def checkCRC(crc1, crc2):
    if crc1 == crc2:
        return True
    else:
        return False


def checkFileTextEquals(filename1, filename2):
    read1 = readFromFile(filename1)
    read2 = readFromFile(filename2)
    if read1 == read2:
        return True
    else:
        return False


def checkArgv():
    global file
    if len(sys.argv) < 2:
        file="bits.txt"
    else:
        file = sys.argv[1]


def code():
    if file=="bits.txt":
        generate(file)
    read = readFromFile(file)
    crc = countCRC(read)
    merge = addCRC(read, crc)
    converted = convert(merge)
    saveToFile("score.txt", "01111110" + converted + "01111110")


def decode():
    read = readFromFile("score.txt")
    deconverted = deconvert(read)
    validCrc=deconverted[deconverted.__len__()-32:]
    deconverted=deconverted[:deconverted.__len__()-32]
    crc=countCRC(deconverted)
    if checkCRC(validCrc,crc):
        print("CRC is valid")
        saveToFile("bitsReceived.txt",deconverted)
    else:
        print("CRC is invalid!")
    if checkFileTextEquals(file, "bitsReceived.txt"):
        print("The same message")
    else:
        print("Different message")


checkArgv()
code()
decode()
