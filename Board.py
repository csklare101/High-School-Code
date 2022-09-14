# Connect Four for Python, terminal based
# By Carson Sklare
class Board:
    def __init__(self, w, h):
        self.width = w
        self.height = h
        self.current_player = 'R'
        self.board = [[0 for x in range(w)] for y in range(h)]
        self.moves = 0

    def get_width(self):
        return self.width

    def get_height(self):
        return self.height

    def set_board(self, x, y, c):
        self.board[x][y] = c

    def get_space(self, x, y):
        return self.board[x][y]

    def get_moves(self):
        return self.moves

    def make_board(self):
        for i in range(self.width):
            for x in range(self.height):
                self.board[i][x] = '.'

    def last_player(self):
        if self.current_player == 'R':
            return 'Y'
        else:
            return 'R'

    def get_player(self):
        return self.current_player

    def change_player(self):
        if self.current_player == 'R':
            self.current_player = 'Y'
        else:
            self.current_player = 'R'

    def is_connected(self, x, y):
        num = self.board[x][y]
        count = 0
        i = y

        while i < self.get_width() and self.board[x][i] == num:
            count += 1
            i += 1
        i = y - 1
        while count >= 0 and self.board[x][i] == num:
            count += 1
            i -= 1
        if count == 4:
            return True
        count = 0
        j = x
        while j < self.get_height() and self.board[j][y] == num:
            count += 1
            j += 1
        if count == 4:
            return True
        count = 0
        i = x
        j = y
        while i < self.get_width() and j < self.get_height(
        ) and self.board[i][j] == num:
            count += 1
            i += 1
            j += 1
        i = x - 1
        j = y - 1
        while i >= 0 and j >= 0 and self.board[i][j] == num:
            count += 1
            i -= 1
            j -= 1
        if count == 4:
            return True
        count = 0
        i = x
        j = y
        while i < self.get_width() and j >= 0 and self.board[i][j] == num:
            count += 1
            i += 1
            j -= 1
        i = x - 1
        j = y + 1
        while i >= 0 and j < self.get_height() and self.board[i][j] == num:
            count += 1
            i -= 1
            j += 1
        if count == 4:
            return True
        return False

    def drop_disk(self, row):
        i = 0
        for i in range(self.get_height()):
            if self.board[i][row] == self.get_player(
            ) or self.board[i][row] == self.last_player():
                self.board[i - 1][row] = self.get_player()
                break
            i += 1
        if i == self.get_height():
            self.board[i - 1][row] = self.get_player()
        self.moves += 1
        return self.is_connected(i - 1, row)

    def random_move(self):
        from random import randint
        return randint(0, 7)

    def can_play(self, col):
        return self.board[0][col] != self.get_player()

    def is_full(self):
        return self.moves == self.get_height() * self.get_width()

    def to_string(self):
        bstring = ""
        for i in range(self.get_width()):
            for x in range(self.get_height()):
                bstring += self.get_space(i, x) + " "
                x += 1
            bstring += "\n"
            i += 1
        for j in range(self.get_width()):
            bstring += str(j) + " "
        bstring += "\n"
        return bstring

    def player_move(self, col):
        print("\n\n Player ", b.get_player(), ":")
        if col >= 0 and col < 8 and self.can_play(col):
            if self.drop_disk(column):
                print("\n\nPlayer ", b.get_player(), " wins!")
                return
            b.change_player()
            return
        else:
            print("Sorry that move cannot be played, try again")


b = Board(8, 8)
gamePlay = True
singlePlayer = False
pPlay = True
while (gamePlay):
    xPlay = True
    yPlay = False
    print("Welcome to Connect Four")
    print("\nWould you like to play single player against a random AI or multiplayer?")
    print("Type \"s\" to play single player, type \"m\" to play multiplayer, the default is multiplayer")
    mode = input()
    if mode == "s":
        singlePlayer = True
        print("SINGLE PLAYER")
    else:
        singlePlayer = False
        print("MULTIPLAYER")
    print("\nWho wants to go first, red or yellow?\nType \"r\" or \"y\", default is red")
    first = input()
    if first == 'y' and b.get_player() == 'R':
        print("\nYellow goes first")
        b.change_player()
    elif first == 'y' and b.get_player() == 'Y':
        print("\nYellow goes first")
    elif b.get_player() == 'Y' and not first == 'y':
        print("\nRed goes first")
        b.change_player()
    else:
        print("\nRed goes first")
    b.make_board()

    while pPlay:
        column = 0
        while xPlay:
            print("\n\nPlayer ", b.get_player(), ":")
            while True:
                try:
                    column = int(input())
                    break
                except:
                    print("Non valid")
            if column >= 0 and column < 8 and b.can_play(column):
                if b.drop_disk(column):
                    print("\n\nPlayer ", b.get_player(), " wins!")
                    xPlay = False
                    pPlay = False

                    break
                b.change_player()
                xPlay = False
                yPlay = True
                break
            else:
                print("Sorry that moved cannot be played, try again.")
        print(b.to_string())
        while yPlay:
            print("\n\nPlayer ", b.get_player(), ":")
            if singlePlayer:
                if column >= 0 and column < 8 and b.can_play(b.random_move()):
                    if b.drop_disk(b.random_move()):
                        print("\n\nPlayer ", b.get_player(), " wins!")
                        yPlay = False
                        pPlay = False

                        break
                    b.change_player()
                    #print(b.to_string())
                    yPlay = False
                    xPlay = True

                    break
                else:
                    print("Sorry that moved cannot be played, try again.")
            else:
                while True:
                    try:
                        column = int(input())
                        break
                    except:
                        print("Non valid")
                if column >= 0 and column < 8 and b.can_play(column):
                    if b.drop_disk(column):
                        print("\n\nPlayer ", b.get_player(), " wins!")
                        xPlay = False
                        pPlay = False
                        break
                    b.change_player()
                    xPlay = True
                    yPlay = False
                    break
                else:
                    print("Sorry that moved cannot be played, try again.")
        print(b.to_string())
        if b.is_full():
            print("Game Draw")
            yPlay = False
            xPlay = False
            break
    print("\n\nWould you like to play again. \"y\" or \"n\"")
    choice = str(input())
    if choice == 'y':
        import os
        os.system('cls')
        pPlay = True
    else:
        print("Bye, come again")
        gamePlay = False
        break
