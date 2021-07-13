import math
import random
import time
import sys

WIRE_LENGTH = 100
A_POS = 0
B_POS = math.floor(WIRE_LENGTH / 2)
C_POS = WIRE_LENGTH - 1
DELAY = 0.01
SESSION = WIRE_LENGTH * 3 * DELAY


class router:
    def __init__(self, place, start, time, done, go, counter, finish, collision, cols, tab):
        self.time = time
        self.tab = tab
        self.cols = cols
        self.collision = collision
        self.finish = finish
        self.counter = counter
        self.go = go
        self.done = done
        self.place = place
        self.start = start


def go(p):
    rand = random.randint(0, 100)
    if p < rand:
        return True
    else:
        return False


def csma_cd(level):
    if level > 16:
        return -1
    if level > 10:
        return random.randint(0, pow(2, 10) - 1)
    else:
        val = random.randint(0, pow(2, level) - 1)
        return val


def start():
    routerA = router(A_POS, A_POS, -1, False, False, 0, False, False, 0, [" " for x in range(WIRE_LENGTH)])
    routerB = router(B_POS, B_POS, -1, False, False, 0, False, False, 0, [" " for x in range(WIRE_LENGTH)])
    routerC = router(C_POS, C_POS, -1, False, False, 0, False, False, 0, [" " for x in range(WIRE_LENGTH)])
    timeCounter = 0
    file = open("trash.txt", "w")
    if len(sys.argv) == 4:
        delA = SESSION / float(sys.argv[1])
        delB = SESSION / float(sys.argv[2])
        delC = SESSION / float(sys.argv[3])
    elif len(sys.argv) == 3:
        delA = SESSION / float(sys.argv[1])
        delB = SESSION / float(sys.argv[2])
        delC = 100000
        routerC.done = True
    elif len(sys.argv) == 2:
        delA = SESSION / float(sys.argv[1])
        delB = 10000
        routerB.done = True
        delC = 10000
        routerC.done = True
    else:
        delA = 0
        delB = 10000
        routerB.done = True
        delC = SESSION / 5
    while True:
        if routerA.done and routerB.done and routerC.done:
            print("END")
            return
        if timeCounter >= SESSION:
            if not routerA.go and not routerB.go and not routerC.go:
                timeCounter = 0
            routerA.time -= 1
            routerB.time -= 1
            routerC.time -= 1
        if timeCounter > delA and routerB.tab[A_POS] == " " and routerC.tab[A_POS] == " ":
            routerA.go = True
            routerA.finish = False
            routerA.collision = False
            delA = 10000000
        if timeCounter > delB and routerC.tab[B_POS] == " " and routerA.tab[B_POS] == " ":
            routerB.go = True
            routerB.finish = False
            routerB.collision = False
            delB = 10000000
        if timeCounter > delC and routerB.tab[C_POS] == " " and routerA.tab[C_POS] == " ":
            routerC.go = True
            routerC.finish = False
            routerC.collision = False
            delC = 10000000
        if not routerA.go and routerA.time == 0 and routerB.tab[A_POS] == " " and routerC.tab[A_POS] == " ":
            routerA.go = True
            routerA.finish = False
            routerA.collision = False
        if not routerB.go and routerB.time == 0 and routerC.tab[B_POS] == " " and routerA.tab[B_POS] == " ":
            routerB.go = True
            routerB.finish = False
            routerB.collision = False
        if not routerC.go and routerC.time == 0 and routerB.tab[C_POS] == " " and routerA.tab[C_POS] == " ":
            routerC.go = True
            routerC.finish = False
            routerC.collision = False
        if routerA.go:
            if not routerA.finish:
                if routerA.place < WIRE_LENGTH:
                    routerA.tab[routerA.place] = "A"
                    routerA.place += 1
                routerA.counter += 1
            if routerA.finish and not routerA.collision:
                routerA.tab[routerA.start] = " "
                routerA.start += 1
                if routerA.start == WIRE_LENGTH:
                    routerA.done = True
                    routerA.go = False
                    routerA.start = A_POS
                    routerA.place = A_POS
                    routerA.counter = 0
                    if timeCounter < SESSION:
                        routerA.time -= 1
                        routerB.time -= 1
                        routerC.time -= 1
                    timeCounter = 0
            if routerA.collision:
                routerA.tab[routerA.start] = " "
                routerA.start += 1
                if routerA.start == WIRE_LENGTH:
                    routerA.go = False
                    routerA.start = A_POS
                    routerA.place = A_POS
                    routerA.counter = 0
                    timeCounter = 0
            if routerA.counter == WIRE_LENGTH * 2:
                routerA.finish = True
            if not routerA.collision and (routerB.tab[A_POS] != " " or routerC.tab[A_POS] != " "):
                routerA.cols += 1
                routerA.time = csma_cd(routerA.cols)
                routerA.collision = True
        if routerB.go:
            if not routerB.finish:
                if routerB.place < WIRE_LENGTH:
                    routerB.tab[routerB.place] = "B"
                    routerB.tab[WIRE_LENGTH - routerB.place - 1] = "B"
                    routerB.place += 1
                routerB.counter += 1
            if routerB.finish and not routerB.collision:
                routerB.tab[routerB.start] = " "
                routerB.tab[WIRE_LENGTH - routerB.start - 1] = " "
                routerB.start += 1
                if routerB.start == WIRE_LENGTH:
                    routerB.done = True
                    routerB.go = False
                    routerB.start = B_POS
                    routerB.place = B_POS
                    routerB.counter = 0
                    if timeCounter < SESSION:
                        routerA.time -= 1
                        routerB.time -= 1
                        routerC.time -= 1
                    timeCounter = 0
            if routerB.collision:
                routerB.tab[routerB.start] = " "
                routerB.tab[WIRE_LENGTH - routerB.start - 1] = " "
                routerB.start += 1
                if routerB.start == WIRE_LENGTH:
                    routerB.go = False
                    routerB.start = B_POS
                    routerB.place = B_POS
                    routerB.counter = 0
                    timeCounter = 0
            if routerB.counter == WIRE_LENGTH * 2.5:
                routerB.finish = True
            if not routerB.collision and (routerA.tab[B_POS] != " " or routerC.tab[B_POS] != " "):
                routerB.cols += 1
                routerB.time = csma_cd(routerB.cols)
                routerB.collision = True
        if routerC.go:
            if not routerC.finish:
                if routerC.place >= 0:
                    routerC.tab[routerC.place] = "C"
                    routerC.place -= 1
                routerC.counter += 1
            if routerC.finish and not routerC.collision:
                routerC.tab[routerC.start] = " "
                routerC.start -= 1
                if routerC.start == -1:
                    routerC.done = True
                    routerC.go = False
                    routerC.start = C_POS
                    routerC.place = C_POS
                    routerC.counter = 0
                    if timeCounter < SESSION:
                        routerA.time -= 1
                        routerB.time -= 1
                        routerC.time -= 1
                    timeCounter = 0
            if routerC.collision:
                routerC.tab[routerC.start] = " "
                routerC.start -= 1
                if routerC.start == -1:
                    routerC.go = False
                    routerC.start = C_POS
                    routerC.place = C_POS
                    routerC.counter = 0
                    timeCounter = 0
            if routerC.counter == WIRE_LENGTH * 2:
                routerC.finish = True
            if not routerC.collision and (routerB.tab[C_POS] != " " or routerA.tab[C_POS] != " "):
                routerC.cols += 1
                routerC.time = csma_cd(routerC.cols)
                routerC.collision = True

        for str in routerA.tab:
            print(str, end="")
            if str == " ":
                file.write(" ")
            else:
                file.write(str)
        print("")
        file.write("\n")
        for str in routerB.tab:
            print(str, end="")
            if str == " ":
                file.write(" ")
            else:
                file.write(str)
        print("")
        file.write("\n")
        for str in routerC.tab:
            print(str, end="")
            if str == " ":
                file.write(" ")
            else:
                file.write(str)
        print("")
        file.write("\n")
        file.write("\n")
        timeCounter += DELAY
        time.sleep(DELAY)


start()
