# CS2030S Syntax and Commands

[TOC]



## Ubuntu Terminal Command

[xiaoyunw@PC: ~] command arg 1 arg 2... *(command line arguments)*

- `Ctrl + P` (up) or `Ctrl + N` (down): see previously executed commands
- `pwd`: print/display working directory (current directory/folder)
- `mkdir` <folder name>: create new folder in current folder/directory (relative path)
- `cd` <folder name or directory path>: change directory to this folder
  - e.g. `cd NUS` == `cd /home/xiaoyunw/nus` 
  - e.g. can also use relative path `cd ../../two` (a relative path NEVER starts with `/`)
  - `cd` or `cd ~`automatically goes back to the home directory
  - `cd /` goes to the roots directory
  - `cd /mnt/c/Users/<your username>` 
  - `cd /mnt/<drive letter>`
- If you need to transfer files between the WSL installation and Windows, you can open Windows Explorer by typing `explorer.exe .` in WSL.
- `ls`: list everything in current directory
- `ls -l`: display almost all the file information, include the size of the file and the date of modification
- `ls -F`:  for more information
  - when this command is used,`/` beside a filename --> the file is a directory
- 

##### Copy

- `cp` <one> <two>: copy <one> to <two>
- `cp -r` <dir>: copy recursively (used to copy the whole dir)
  - e.g. `cp -r ../workshop .` (use `.` to indicate that we wish to copy the whole sub-tree of `workshop` over the current directory.)
  - `workshop` now exist under the <dir>
  - note: `cp` overwrites if there is an existing file with the same name
- e.g. `cp Hello.java Hello1.java`

##### Move

- `mv` <file> <dir>: move <file> to <dir>
  - e.g. `mv Hello.java ..` (signifies going one level up from current dir)
  - note: `mv` overwrites if there is an existing file with the same name w/o warning
- `mv -i`: asks for permission to overwrite (if any)
  - type `Y` to overwrite
- `mv` <file1> <file2>: rename <file1> to <file2> 
  - e.g. mv Hello.java Bye.java

##### Remove

- `rm` <file>: remove <file>
- `rmdir` <dir>: remove <dir> 
- `rm -rf` <folder>: recursively and forcefully remove <folder>; usually used for folders containing other folders (directories)
- `rm -rf *`: remove ALL files forcefully
- `rm -i`: asks for permission to delete
  - type `Y` to delete

##### Catenate

- `cat` <file>: reads the content of the file and prints it to standard output (terminal).
  - prints the content in the file
  - if no name is specified, command will wait for you to type sth
  - `Ctrl + D` to send the end-of-input command to cat
- `less`: a variant of `cat` that included features to read each page leisurely and is useful for long files
  - `<space>` to move down one page
  - `b` to move back one page
  - `q` to quit
- `wc` <file>: counts no. of lines, words, characters in the file
  - no filename mentioned -> same as `cat`

```java
$ wc test.txt
       1      11      64 test.txt
// 1 line, 11 words, 64 characters
```

##### Miscellaneous(?)

- `Tab`: complete the name
  - e.g. `mv t` + `Tab` - command prompt will complete the filename for you if there is only one filename with the prefix "t"
- `man`: online manual
  - e.g. `man ls`
  - `q` to quit

##### Permissions

| Permission | Effect on a file              | Effect on a directory                                        |
| ---------- | ----------------------------- | ------------------------------------------------------------ |
| `r` (4)    | reading the content of a file | read the names of the files in the directory                 |
| `w` (2)    | writing into a file           | create/delete/rename files in the directory                  |
| `x` (1)    | executing a file              | access contents and meta-info (size, creation time) of files in the directory |

- Symbolic Notation:
  - `rwx` - read, write, execute all permitted
  - `r-x` - read, execute permitted only
  - `-wx` - write, execute permitted only
- Numerical notation:
  - `rwx` = 4 + 2 + 1 = 7
  - `r-x` = 4 + 1 = 5
  - `-wx` = 2 + 1 = 3

- `u` is the **u**ser who owns the file; 
- `g` refers to the users in the same **g**roup as the user; and 
- `o` are all the **o**ther users.
- The permission of 644, or `rw-r--r--`, on a file means that:
  - the owner can read and write
  - the group users can only read
  - all the other users can only read
- `ls -l` <file>: view permission of <file>
- `chmod`: change permissions of a file or dir

```java
$ ls -l test.txt
-rw-r--r--@ 1 ooiwt  staff  64 Jul 28 09:52 test.txt
// Ignoring the first - and the last @, the permission of test.txt is 644. 

$ chmod 666 test.txt
$ ls -l test.txt
-rw-rw-rw-@ 1 ooiwt  staff  64 Jul 28 09:52 test.txt   

$ chmod o-w test.txt
$ ls -l test.txt
-rw-rw-r--@ 1 ooiwt  staff  64 Jul 28 09:52 test.txt
// remove write permission from other users
```

- If you use the SoC Unix server to do your homework, you should prevent the directory that stores your homework from being accessible by other users. Make sure that your homework directory as the permission of `700`.
- If you download a file from the Internet and you do not have the permission to read it, you should do a `u+r` to give yourself the read permission.
- A program should have execution permission to run. If you have a script or an executable file that you can't run, give yourself the execution permission `u+x`.



- `vim` <file>: open <file> in vim editor
  - e.g. `vim Hello.java` (if non-existent, will create java file with the name)
- `vim -p` <files>: open <files> in Vim in separate tabs 
- EDIT - COMPILE - RUN: 
  - `vim Hello.java`
  - `javac Hello.java` (compile to create Hello.class)
  - `java Hello` (run this prog to create prog output)
- `vim test.in` : create a text doc
- `java Hello < test.in`: supplies the java programme with input from test.in doc
- `vim -p *.java` --> `javadoc *.java` --> `chromium index.html` - to generate and see ur documentation

- standard input refers to the *keyboard* 
- standard output is the *terminal*.

##### Output Redirection

- `>`: redirect the standard output to a file & overwrite the given file
- `>>`: concatenate into the given file

```java
$ wc test.txt > test.count // redirects the output from wc to a file named test.count
$ cat test.count
       1      11      64 test.txt // overwritten
// original output from wc is now stored in the file test.count.
    
$ wc test.txt >> test.count
$ cat test.count
       1      11      64 test.txt
       1      11      64 test.txt
// a new line is concatenated into test.count. So the file now has two lines.
```

##### Input Redirection

- `<`: used to redirect a file into the standard input, instead of reading from the keyboard, we can now read from a file
- Commands such as `cat` and `wc` already support from a file directly, so there is no difference in terms of functionality to run the commands by passing in the file name, or by using the `<` operator.

```java
$ wc test.txt
       1      11      64 test.txt
$ wc < test.txt
       1      11      64
// no longer prints the file name since from wc points of view, it is read from the standard input and not from a file, so it is not aware of the file named test.txt
$ cat test.txt | wc // same as the above command line; compose cat & wc tgt
       1      11      64    
// pipe the standard output from cat to wc. So now, these printed texts are redirected as the standard input to wc.
    
$ cat test.txt
This is a test file for learning Unix file management commands.
$ cat < test.txt
This is a test file for learning Unix file management commands.
```



##### Useful Utilities

- `|`

```java
$ cat test.txt foo.txt bar.txt | wc
       3      33     192
// cat read the three files, concatenate their content, and pass the output to wc for counting. Alternatively,
$ cat *.txt | wc
// * rep 0 or more characters, i.e. cat any files that contain 0 or more characters with .txt forma

$ ls ???.txt
bar.txt foo.txt
// matches any file name with three characters followed by .txt.

$ ls [f-t]*t
foo.txt test.txt
// matches all file names the start with alphabet f, g, etc, until t, followed by zero or more characters, followed by t.
    
$ ls {fo,ba}??txt   
bar.txt foo.txt
// matches any file names the start with either fo or ba, followed by two characters, followed by txt.
```

| Pattern      | Matches                                                      |
| ------------ | ------------------------------------------------------------ |
| `*`          | 0 or more characters                                         |
| `?`          | one character                                                |
| `[...]`      | one character, coming from the given set between `[ ]` , `-` to indicate a range |
| `{..., ...}` | either one of the names, separated by `,`                    |



- `head`, `tail`: prints out the first k lines and last k lines from a file (or standard input if the file name is not given). 
  - By default, k is 10, but you can pass in an argument to specify k.

```
$ cat test.txt foo.txt bar.txt
This is a test file for learning Unix file management commands.
This is a test file for learning Unix file management commands.
This is a test file for learning Unix file management commands.
$ cat test.txt foo.txt bar.txt | tail -1
This is a test file for learning Unix file management commands.
```

- `echo`: prints out the command-line argument to the standard output.

```
$ echo "hello world!"
hello world!
```

- `sort`: rearrange the input lines in alphabetical order.
  - `Ctrl + D` to end

```java
$ sort
john
jane
peter
mary^D // end of user input
jane
john
mary
peter
```

- `uniq`: remove any two consecutive lines that are the same.

```java
$ uniq
1
2
2
2
1
1^D // end of user input
1
2
1
```

##### Pipe

```java
// file ID
A1234567X,CS
A1234559A,CEG
A1239999J,CEG
A1234580K,CEG
A1233210O,CS
A1234567X,CS
A1234581Q,ISC
A1233216T,ISC
A1239999J,CEG
    
// to count how many unique registrants
$ cat ID | sort | uniq | wc -l
    7
    
// to count how many unique registrants are CEG students
$ cat ID | sort | uniq | grep CEG | wc -l
    3
// If we need to run this over and over again or share this command with someone, we can put these commands in a file (aka shell script), and then run it by invoking its name. 
$ cat > hello.sh
echo hello!^D
$ bash hello.sh // or
hello!
$ bash < hello.sh
hello!
```



## Vim Commands

https://vim.rtorr.com/

**Normal / Command Mode**

<img src="C:\Users\jieqi\AppData\Roaming\Typora\typora-user-images\image-20210111210851408.png" alt="image-20210111210851408" style="zoom:33%;" />

- `help` <keyword>
- `:h vim-modes` - read about vim modes
- `x` - delete current character
- `u` - undo
- `Ctrl + R` - redo
- `w` - go to the next word
- `b` - go to the previous word
- <n> <movement> - apply <movement> <n> times
  - e.g. `10w` - move 10 words down   
  - e.g. `100j or down` - move 100 lines down
- `gg` - go to the 1st line in file
- `G` - go to the last line in file
- `gg=G` - fix indentation in the entire file
  - avoid using where indentation can change the behaviour of the code e.g. in python
- `:`<n> - move to line <n>
- `/`<word> - search for text that match <word>
- `N` - jump to the previous occurrence of <word>
- `n` - jump to the next occurrence of <word> 
- `gt` - go to the next tab
- `gT` - go to the previous tab
- `:split` or `:sp` <file> - split screen for buffer <file> horizontally
- `:vsplit` or `:vsp` <file> - split screen for buffer <file> vertically
- `:e`  <filename> - open another file
- `Ctrl + W` <direction keys LeftRightUpDown> - go to the window on the <direction keys LRUD>
- `Ctrl + w Ctrl + w` - moves between the different vim viewports
- `:tabedit Main.java` - open Main.java in a separate tab
- `/` <word> `D`<num>`W` - delete <num> of words starting from <word>
- `(` - move forward by one sentence
- `)` - move backward by one sentence
- `{` - move forward by one paragraph
- `} ` - move backward by one paragraph
- `0` - move to the beginning of a line (not sentence)
- `$` - move to the end of a line 
- `dd` - remove the whole line
  - repeating the same command twice means applying it to the whole line
- `D` or `shift + d` 
  - `shift` means applying the action from the current cursor until the end of the line.  
- `yy` - copy/yank current line
- `:%s/oldWord/newWord/gc` - search and replace
  - `%` - apply to the whole doc
  - `s` - substitute
  - `g` - global (otherwise, only the first occurrence of each line is replaced)
  - `c` - cause vim to confirm with you before each replacement
- movement - action - movement pattern
  - e.g. `(y)` - move to the beginning of the sentence, yank, and until the end of the sentence, i.e. copy the current sentence
  - e.g. `yy9p` - copy the current line, paste 9 times



**Insert Mode** (press `I or S or A or D` in Normal Mode)

- `Esc` to exit insert mode
- `A` - appends the text typed to the end of the current line
- `O` - opens up a new line for your text
- `S` - substitute the current character with your text
- similarly, pressing together with `Shift` applies the action to the entire line 
- `Ctrl + P` or `Ctrl + N` to auto-complete the current word (useful for method and variable names)



**Command Line Mode**

- `:w` or `:write` - save changes / write buffers to files
- `:q` or `:quit` - quit Vim, back to terminal; close Vim window
  - or `Shift + Z + Z`
- `:q!`  - quit forcefully w/o saving changes
- `:wq` - save changes and quit
- `:wq!` - save changes and quit forcefully, overwrite previous changes
- `:wa` - write all buffers to respective files
- `:wqa` - write all buffers to respective files, then quit Vim
- `:r!` <command>
  - asks vim to read sth and paste it into the current cursor location
  - `:r! date`  - insert today's date
  - `:r! cal` - insert calendar
  - `:r! ls *jpg` - insert the list of all JPG pics



**Visual Mode** (press `V` in Normal Mode)

- highlight text by moving the cursor
- `d` - cut
- `y` - copy
- `p` - paste

**Visual Line Mode** (press `Shift + V` in Normal Mode)

- highlight whole line
- after highlighting, can hit `:`
- `:'<,'>w john.txt` - write the highlighted text into a file named `john.txt`
- `:'<,'>!fmt` - format text in standard output
- `:'<,'>!wc` - replace the highlighted text with the output from `wc`

**Visual Block Mode** (press `Ctrl + V` in Normal Mode)

- highlight selected block



## JShell

- Read-Eval-Print-Loop (REPL)
- same as the right panel in SourceAcademy
- `/open Student.java`
- `jshell Student.java`
- if your class  contains another class, input the latter class first
  - e.g. `Circle contains Point`
  - `jshell Point.java Circle.java`
- `/list` - prints the contents that has been inputted



## Java Syntax

### Console Input

```java
System.out.println("String"); // prints to a new line
System.out.print("String");

import java.io.*; 
  
class GFG { 
    public static void main(String[] args) { 
        // Declaring variable 
        int num1 = 10, num2 = 20, sum; 
  
        // Printing the variables 
        System.out.print("The addition of "); 
        System.out.println( 
            num1 + " and " + num2 + " is:"); 
  
        // Printing the result after operation 
        System.out.println(num1 + num2); 
    } 
} 
/* output:
The addition of 10 and 20 is:
30 */

// Java code to illustrate difference 
// between print() and println() 
  
import java.io.*; 
  
class Demo_print { 
    public static void main(String[] args) 
    { 
        System.out.println("Using print()"); 
  
        // using print() 
        // all are printed in the 
        // same line 
        System.out.print("GfG! "); 
        System.out.print("GfG! "); 
        System.out.print("GfG! "); 
  
        System.out.println(); 
        System.out.println(); 
        System.out.println("Using println()"); 
  
        // using println() 
        // all are printed in the 
        // different line 
        System.out.println("GfG! "); 
        System.out.println("GfG! "); 
        System.out.println("GfG! "); 
    } 
} 
/*Using print()
GfG! GfG! GfG! 

Using println()
GfG! 
GfG! 
GfG! */ 
```

### Console Output

```java
import java.util.Scanner;

class DriverClass {
    public static void main(String[] args) {
    	Scanner sc = new Scanner(System.in); // use this only
        int someInt = sc.nextInt();
        double someDouble= sc.nextDouble();
        String someLine = sc.nextLine();
        String someWord = sc.next(); // stop at white space
        Scanner sc2 = new Scanner(Systm.in) // DO NOT use another oe
}
```

<img src="C:\Users\jieqi\AppData\Roaming\Typora\typora-user-images\image-20210111221217885.png" alt="image-20210111221217885" style="zoom:33%;" /> <img src="C:\Users\jieqi\AppData\Roaming\Typora\typora-user-images\image-20210111231650380.png" alt="image-20210111231650380" style="zoom:33%;" />

- `sc.nextLine()` captures the entire line 
- `sc.next()` captures characters until it reaches the next space
- for CS2030S, use only a single Scanner if needed

### Strings with Placeholders

```java
System.out.printf("%d %f", var1, var2);
String s = String.format("%d\n", var1); // \n --> new line 
```

### Comments

```java
// single line comment
/*
multi-line comment
*/
```

### JavaDocs 

```java
/** note 2 asteriks
* The main method that drives the program.
* @param description of a param; args the command line arguments supplied on execution
* @return bar
* @throws RuntimeException if exception
* a description of what this program does, i.e. documentation
* supports html styles 
* <p> </p>
* Link to another program {@link Foo}
* Link to a method/class foo in Foo {@link Foo#foo}
* Link to anotherBar in Bar {@link #another Bar}
* Link specifying text of the hyperlink {@link Foo the Foo class}
* Link to an overloaded method {@link Foo#foo(int)}
*/
```

### Variables

```java
// Declarations
int i;
double j;
ClassName k;
int[] m;

// Initialisations
int i = 1;
double j = 4.0;
boolean k = true;
int[] m = new int[5];
int[] n = new int[] {1, 2, 3, 4, 5};
ClassName n = new ClassName(arg1, arg2);

// Typecasting
(int) i;
(double) i;
```

- primitive types (stored as it is, not as reference): 
  - byte
  - short (integer types)
  - **int** (integer types) [division of int gives the floor of the result of the regular division]
  - long (integer types)
  - float (rational types)
  - **double** (rational types)
  - boolean
  - char

```java
// for primitive types 

int i = 4;
int j = i;
i++;
System.out.println(j); // output 4
```

### Math Operators

```java
1 + 1 // 2
2 - 1 // 1
3 * 4 // 12
4 / 3 // gives the math.floor: 1
4.0 / 3 // 1.333...
Math.pow(2, 3) // 2^3: 8
"a" + "b" // "ab"
    
i++; // evaluate to the value of i, then increment by 1
    int i = 0;
	int[] arr = new int[] {0, 1, 2, 3, 4};
	arr[i++] // evaluates to arr[0] or 0,
    // after the prev line, i increases by 1 to become 1
    
++i; // increment by 1, then evaluates to the new value of i
    int i = 0;
	int[] arr = new int[] {0, 1, 2, 3, 4};
	arr[++i] // increments i first, then evaluates to arr[1] which is 1
    // after the prev line, i increases by 1 to become 1
        
i--;
--i;
        
// AVOID the following eg if unsure
arr[--i] = i++            
```

### Logical Operators

| Symbol   | Definition         |
| -------- | ------------------ |
| !a       | not a              |
| a && b   | a and b            |
| a \|\| b | a or b             |
| a ^ b    | exclusive or (XOR) |
| a == b   | equality           |
| a != b   | unequal            |

### Loops

- For

```java
// General
for (int i = 0; i < n; i++) {
	...
}
/* 
(1) initialise loop variables
(2) check loop condition: continue if T, else terminate
(3) run statement block
(4) i++
(5) repeat steps 1 - 4 until (2) evaluates to F
*/

// Enhanced for-loops
int[] intArr = new int[] {1, 2, 3, 4, 5};

for (int i: intArr) {
    System.out.println(i);
}
/*
i takes on the values of arr sequentially
i: 1 -> output 1
i: 2 -> output 2
...
sequence exhausted (no more elements in the arr), loop terminates
*/
```

- While
  - declare var outside the loop

```java
int i = 0;
while (i < n) {
	...
}
```

- Do-While
  - will always execute at least once
  - note the conditions, could carelessly end up with infinite loops

```java
do {
	...
} while (i < n);// rmb the semi-colon

// reverse of while-loops
// run the statement in the do {} before checking for the conditions in while ()
```

```java
public class Main {

    public static void main(String[] args) {
        int count = 1;
        while (count != 6) {
            System.out.println("Count value is: " + count);
            count++;
        }

        // alternative version 1
//        count = 1;
//        while (true) {
//            if (count == 6) {
//                break;
//            }
//            System.out.println("Count value is: " + count);
//            count++;
//        }

        // alternative version 2
        for (int i = 1; i != 6; i++) {
            System.out.println("i value is: " + i);
        }

        // alternative version 3
        count = 1;
        do {
            System.out.println("Count value was: " + count);
            count++;

//            if (count > 100) {
//                break;
//            }
        } while (count != 6);
    }
}
```

- `continue` - the loop will bypass the code block below the `continue` keyword and continue with the next loop iteration
- `break` - exit the loop depending on the condition we are checking

**Functions a.k.a. Methods**

- specify the type of return value and parameters

```java
int MyMethod(int var1, double var2) {
    ... 
    return 1;
}
```

**Array Indexing**

```java
int a = intArr[0];
String b = stringArr[0];
```

**Attributes / Methods Calls**

```java
s.myAttribute = 1;
int x = t.intAttribute
String y = u.methodCall(arg1, arg2)
```



### Spaces

- Stack (call): function, primitive type vars, object ref
- heap: actual object/arrary
- 2D arrays are 1D arrays in 1D arrays 



### Object

- Object is a reference type: if a variable is meant to store an object, the object itself is not stored in the variable, but instead, its reference (or location memory is stored in the variable) 
  - e.g. arrays
- an object has both attributes/states and behaviours
- Attributes (generally fields - encapsulates data): what the object has
  - attribute type can be classes as well
- Behaviours (methods): what the object can do
  - e.g. attributes of a duck - height, mass, gender, name;
  - e.g. methods of a duck - quack, swim, fly

```java
Student s = new Student(1, "Alice");
Student t = s; 
s = ???? // reassigning s here does not change t; s & t take up diff regions in the stack frame
// However, 
s.id = 2; // original was 1
System.out.println(t.id); // output 2 instead of 1

int[] arr = new int[3];
// int[] arr = int[]@1234 (refernce no.)

int[][] c = new int[][] {{1,2}, {3,4}} // no multi-dimensional arr in java, each is 1D arr of multiple 1D arrs; the outer 2D arr holds references to 1D inner arrs which each hold multiple int vals
// int[][] c = new int[][]@3333;

int[] b = c[0];
b[0] = 5;
// int[] b = int[]@1111;
// c becomes [5, 2, 3, 4]

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Example 1 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
public static void main(String[] args) {
    int a = 1;
    coolMethod(a); // arg = 1 
    System.out.println(a);
}

static void coolMethod(int a) {
    a = 2;
}

// in stack call, coolMethod with a as arg is push onto main method
// coolMethod is evaluated to give a = 2 (within the coolMethod frame)
// value of a in the main method frame is not changed. --> output 1
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Example 1 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
public static void main(String[] args) {
    Student a = new Student(1, "Alice");
    coolMethod(a); // coolMethod(Student@1234)
    System.out.println(a.id); // prints 2
}

static void coolMethod(Student a) {
    a.id = 2; // attribute of object referred to by a is changed
}
```

<img src="D:\Google\Downloads\WeChat Image_20210113205904.png" style="zoom:25%;" />

<img src="D:\Google\Downloads\WeChat Image_20210113205858.png" style="zoom:25%;" />



### Object-Oriented-Programming (OOP)

- 4 main principles
  - abstraction - separate interface ("what it is supposed to do") from implementation ("how it does it")
  - encapsulation - hide implementation. Only make interface publicly visible
  - inheritance - build new classes by extending existing classes (shared functionality)
  - polymorphism - same interface, but different behaviour based on context (`animal.vocalize()` meows if animal is a cat, and barks if animal is a dog)

<img src="https://i2.wp.com/www.dineshonjava.com/wp-content/uploads/2017/04/Difference-between-Abstraction-vs-Encapsulation.png?w=530&ssl=1" alt="Difference between Abstraction vs Encapsulation in Java" style="zoom: 67%;" />

| Abstraction                                                  | Encapsulation                                                |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| Abstraction is a general concept formed by extracting common features from specific examples or the act of withdrawing/removing sth unnecessary | Encapsulation is the mechanism that binds together code and the data it manipulates, and keeps both safe from out interface and misuse. |
| Achieve abstraction using **Interface** and **Abstract Class** | Achieve encapsulation using Access Modifiers (`public`, `protected`, `private`) |
| Abstraction solves the problem at Design level               | Encapsulation solves the problem at Implementation level     |
| For simplicity, abstraction hides implement                  | For simplicity, encapsulation means hiding data using getter and setter |



- Class (blueprints/template for creating objects): describes the common attributes and methods that an object has
  
  - each object created from the class is called an instance
  - class could be though of as a powerful user-defined data type, sort of an extra data type, allowing u to create more powerful data.
  - allows us to create variables that can be seen are accessible from anywhere within the class that we are creating (class or member variables, more commonly known as fields)
  - when creating a field for a class, need to specify access modifier 
  - (usually private -> adhere to encapsulation - hide fields and methods from access publicly)
  
  <img src="D:\Google\Downloads\WeChat Image_20210113205808.png" style="zoom: 25%;" />
  
- Constructor: procedure to run upon object creation
  - special type of method, method of a class that defines what to do when u instantiate from it
  - e.g. `new` is most likely followed by a called to a constructor (create new object)

  ```java
  // Examples of constructors
  ClassName (int foo, boolean bar) {
  	...
  }
  
  static ClassName factoryMethod (int foo, boolean bar) {
      return new ClassName(foo, bar);
  }
  ```

- Instance-level attributes/methods: attributes/methods tied to the instance

- Class-level attributes/methods: attributes/methods tied to the class
  - use `static` keyword to bind attribute/method to a class
  - no need to instantiate an object to access them; there's only one copy of them in existence
  - this means that referring to this var (e.g. numOfStudents in class Students) gives the same value when it is called in the instances 
  - *think of box-and-pointer diagram where two variables point to the same box, i.e. they share the same value and changing one value affects the other value*  
  
  <img src="D:\Google\Downloads\WeChat Image_20210113205819.png" style="zoom:25%;" />
  
  <img src="D:\Google\Downloads\WeChat Image_20210113205824.png" style="zoom:25%;" />
  
  <img src="D:\Google\Downloads\WeChat Image_20210113205830.png" style="zoom:25%;" />
  
- Encapsulation - hiding implementation details and state of objects from clients
  
  - use access modifiers

| Modifier                        | Access                                                       |
| ------------------------------- | ------------------------------------------------------------ |
| **public**                      | all (usually for methods)                                    |
| **protected**                   | only available to classes in the same package, and its subclasses (regardless of package - wider accessibility than package-private) |
| **blank (aka package private)** | only available to classes in the same package; default access modifier |
| **private**                     | only available within the same class (usually for member variables) |

<img src="D:\Google\Downloads\WeChat Image_20210113205847.png" style="zoom:25%;" />

- Top Level: only classes, interfaces and enums can exist at the top level, everything else must be included within one of these.
- `final` - variable / attribute* that cannot be changed after initialisation
  - *final attributes must be initialised within a constructor
- `this` - refers to this object or context from which a method is called

```java
[public | private] [abstract | final] class Student {
    // class defintion
}
// if some object is an instance of the class Student, then that instance is a student

<return type> <method name> (<arg1, arg2, ...>) {
    // method definition
}
```



### Application Programming Interface (API)

- a computing interface which defines interactions between multiple software intermediaries
  - e.g. calls, formats, requests, conventions to follow etc (aka how to use a piece of software)
  - docs.oracle.com jdk 11



### Variance

Let C(S) corresponds to some complex type based on type S. 

- An array of type S is an example of a complex type.
- Subtyping is transitive

We say a complex type is:

- *covariant* if S <: T implies C(S) <: C(T)
- *contravariant* if S <: T implies C(T) <: C(S)
- *invariant* if it is neither covariant nor contravariant. 



| Covariant C(S) <: C(T)                                       | Contravariant C(T) <: C(S)                                   | Invariant                                                    |
| ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| <u>Array:</u> <br />==S <: T =>  S[] <: T[]==<br />e.g. Integer[] <: Object[]; String[] <: Object[] |                                                              | <u>Generics:</u> <br />e.g. no subtype relationship between Array<Shape> and Array<Circle> |
| <u>Upper-bounded wildcards:</u><br />==S <: T =>  A<? extends S>  <: A<? extends T>==<br />==A<S>  <: A<? extends S>==<br />e.g. Array<Circle> <: Array<? extends Circle><br />Array<? extends Circle> <: Array<? extends Shape><br />Array<Cirlce> <: Array<? extends Shape> | <u>Lower-bounded wildcards</u>:<br />==S <: T =>  A<? super T> <: A<? super S>==<br />==A<S> <: A<? super S>==<br />e.g. Array<Shape> <: Array<? super Shape><br />Array<? super Shape> <: Array<? super Circle><br />Array<Shape> <: Array<? super Circle> |                                                              |
|                                                              |                                                              |                                                              |
|                                                              |                                                              |                                                              |
|                                                              |                                                              |                                                              |
|                                                              |                                                              |                                                              |



## Udemy Java Notes

### Parsing Values from a String

- can be changed to other types 
  - e.g. `int `, `double`, `float`

```java
public class ParseValFromStr {
    public static void main(String[] args) {
        String numberAsString = "2018"; // 2018.125 for double eg
        System.out.println("numberAsString = " + numberAsString);
        
        int number =  Integer.parseInt(numberAsString); // Integer is the (wrapper) class; 
        // parseInt is a static method to convert the String to an Int
        // note the upper and lower cases
        
        // double number =  Double.parseDouble(numberAsString);
        System.out.println("number = " + number);
        
        numberAsString += 1; // prints 20181
        number += 1; // prints 2019
        
        System.out.println("numberAsString = " + numberAsString);
        System.out.println("number = " + number);
        
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Eg showing error ~~~~~~~~~~~~~~~~~~~~~~~~~`
        String numberAsString = "2018a";
        System.out.println("numberAsString = " + numberAsString); // prints 2018a 

        int number =  Integer.parseInt(numberAsString); // (exception) error converting the String to an Int
        System.out.println("number = " + number);

        numberAsString += 1; // not executed
        number += 1; // not execute
        
        System.out.println("numberAsString = " + numberAsString); // not executed
        System.out.println("number = " + number); // not executed
    }
}
```

### Reading User Input (Scanner)

- Scanner (class) is a simple text scanner that  can parse both primitive types and Strings
  - built-in class of java that allows us to read user import (what users inputs)
  - use methods e.g. `parseInt` internally (convert a string to int type) 
- the method Next returns a string allowing us to save the returned value to a variable
  - `scanner.nextLine();` - read a line of input from the console
- `System.in` - allows you to type input into the console which then gets returned back to the programme.
- `new` used to create an Instance of Scanner, i.e. create new object of type scanner
  - primitive types do not need `new` keyword
- `scanner.close();` - Close the scanner when we are done
  - once closed, using `next` results in error
  - release the underlying memory that the scanner was using internally

```java
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); 
        // allows you to type input into the console which then gets returned back to the program
        
        System.out.println("Enter your year of birth: ");
        int yearOfBirth = scanner.nextInt(); 
        // retrieve input from the console which is parsed to int type; int value is stored in var yearOfBirth

        System.out.println("Enter your name: ");
        String name = scanner.nextLine(); 
        // retrieve a line of input from the console; store in the variable called name
        int age = 2021 - yearOfBirth;

        System.out.println("Your name is " + name + ", and you are " + age + "years old."); 
        // print out the value entered by the user

        scanner.close(); // close the scanner
    }
}

/*
Enter your year of birth: 
2000
Enter your name: 
Your name is , and you are 21 years old.
*/

# the application did not give us the chance to enter our name 
# whenever we read a number from the scanner, the line ends when we pressed the ENTER key
# Hence, after next ENTER or call to nextInt, call nextLine again without assigning it to a variable
# Internally, the scanner is checking for a line separator, and when we hit ENTER, we are typing a line separator which is interpreted as input. When we reach the nextLine method, that retrieves the name. For this nextLine method, the input name becomes ENTER so it is essentially skipped.

// Correct Version
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your year of birth: ");

        boolean hasNextint = scanner.hasNextInt(); // checks if the next input is int
        // if does not qualify, the method will return false; avoid type errors

        if (hasNextint) {
            int yearOfBirth = scanner.nextInt();
            scanner.nextLine(); // hand next line character (ENTER key)

            System.out.println("Enter your name: ");
            String name = scanner.nextLine();
            
            int age = 2021 - yearOfBirth;

            if (age >= 0 && age <= 100) {
                System.out.println("Your name is " + name + ", and you are " + age + " years old.");
            } else {
                System.out.println("Invalid year of birth.");
            }
        } else {
            System.out.println("Unable to parse year of birth");
        }
        
        scanner.close();
    }
}
```

```java
import java.util.Scanner;

public class ReadingUserInput {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int count = 0;
        int sum = 0;

        while (count < 10) {
            System.out.println("Enter number #" + (count + 1) + ": ");
            boolean hasNextInt = scanner.hasNextInt();

            if (hasNextInt) {
                int num = scanner.nextInt();
                sum += num;
                count++;
            } else {
                System.out.println("Invalid Number.");
                // infinite loop if scanner.nextLine is placed in if block
                // instead of after the while loop
            }
            scanner.nextLine();
            // invalid input needs to be read and removed from the scanner
            // before additional content is entered.
            //
            // since the nextLine call handles that by reading input and not storing it,
            // it needs to be after the else statement, not in the if statement.
            // otherwise, the invalid input will not be removed.
        }
        System.out.println("Sum = "+ sum);
        scanner.close();

        // alternative method - endless loop
        while (true) { 
            int order = count + 1;
            System.out.println("Enter number #" + order + ":");
            boolean hasNextInt = scanner.hasNextInt();

            if (hasNextInt) {
                int num = scanner.nextInt();
                count ++;
                sum += num;
                if (count == 10) {
                    break; // jumps to scanner.close();
                }
            } else  {
                System.out.println("Invalid Number.");
            }
            scanner.nextLine(); // handle end of line (ENTER key)
        }
        System.out.println("sum = "+ sum);
        scanner.close();

    }
}
```

prints max and min value when user key in an invalid input

```java
import java.util.Scanner;

public class MinAndMaxInput {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int max = 0;
        int min = 0; // will introduce a bug if user did not enter 0
        boolean first = true; // flag - boolean T/F

        // alternative solution to flag
//        int min = Integer.MAX_VALUE;
//        int max = Integer.MIN_VALUE;

        while (true) {
            System.out.println("Enter number: ");
            boolean hasNextInt = scanner.hasNextInt();

            if (hasNextInt) {
                int num = scanner.nextInt();

                if (first) {
                    first = false;
                    min = num;
                    max = num;
                }
                // after the user enters the first number, we want to set the bool/flag
                // to false since the user can only enter one 1st number
                // and then set the min and max to the first num entered
                // this if block will only execute once since first is set to F from here on;

                max = Math.max(max, num);
                min = Math.min(min, num);

                // alternative
//                if (num > max) {
//                    max = num;
//                } else if (num < min) {
//                    min = num;
//                }
            } else {
                System.out.println("Maximum no. = " + max);
                System.out.println("Minimum no. = " + min);
                break;
            }
            scanner.nextLine();
        }
        scanner.close();

    }
}
```

### Class, Instance, Object, Reference

- Using the analogy of building a house
- **Class**: a blueprint for a house
  - using the blueprint (plans), we  can build as many houses as we like based on those plans.
- Each house you build (i.e. in other words, **instantiate** using the `new` keyword/operator), is an **object** a.k.a. an **instance**.
-  Each house you build has an address (a physical location). 
- If you want to tell someone where you live, you give them your address (perhaps written on a piece of paper). This is known as a **reference**.
- You can copy that **reference** as many times as you like but there is still just one house. 
  - in other words, we are copying the paper that has address on it but not the house itself
- we can pass **references** as **parameters** to **constructors** and **methods**.

<img src="C:\Users\jieqi\AppData\Roaming\Typora\typora-user-images\image-20210116230747821.png" alt="image-20210116230747821" style="zoom: 33%;" />

<img src="C:\Users\jieqi\AppData\Roaming\Typora\typora-user-images\image-20210116230827543.png" alt="image-20210116230827543" style="zoom: 33%;" />

<img src="C:\Users\jieqi\AppData\Roaming\Typora\typora-user-images\image-20210116230911276.png" alt="image-20210116230911276" style="zoom: 33%;" />

<img src="C:\Users\jieqi\AppData\Roaming\Typora\typora-user-images\image-20210116231024120.png" alt="image-20210116231024120" style="zoom:33%;" />

<img src="C:\Users\jieqi\AppData\Roaming\Typora\typora-user-images\image-20210116231104085.png" alt="image-20210116231104085" style="zoom:33%;" />

<img src="C:\Users\jieqi\AppData\Roaming\Typora\typora-user-images\image-20210116231202452.png" alt="image-20210116231202452" style="zoom:33%;" />

<img src="C:\Users\jieqi\AppData\Roaming\Typora\typora-user-images\image-20210116231255304.png" alt="image-20210116231255304" style="zoom:33%;" />

<img src="C:\Users\jieqi\AppData\Roaming\Typora\typora-user-images\image-20210116231339603.png" alt="image-20210116231339603" style="zoom:33%;" />

<img src="C:\Users\jieqi\AppData\Roaming\Typora\typora-user-images\image-20210116231404907.png" alt="image-20210116231404907" style="zoom:33%;" />

- Note: in Java, you always have references to an object in memory, there is not way to access an object directly. Everything is done using a reference (except for primitives).

```java
// Main class

public class Main {

    public static void main(String[] args) {
        // unlike data types there isn't default values with classes,
        // hence u need to initialize them using the new keyword
        // Car porsche; --> gives error at porsche.setModel
	    Car porsche = new Car(); // define/create an object of type/template Car named porsche
        Car holden = new Car(); // attain extra functionalities when created
                                // inherited from the Java functionality
        // i.e. the Car class has inherited these from a base Java class.

        System.out.println("Model is " + porsche.getModel());
        // prints Model is null (internal default state for a class, including string)

        porsche.setModel("Carrera");
        System.out.println("Model is " + porsche.getModel()); // prints Model is Carrera
    }
}

// Car class
public class Car {

    // declaring class variables or fields which
    // in this case, are the state component for a Car
    // private: does not allow classes outside this Car class to access the vars
    // public: allow access to all
    private int doors;
    private int wheels;
    private String model;
    private String engine;
    private String colour;
    
    // setting up rules to determine which inputs are valid
    public void setModel (String model) {
        String validModel = model.toLowerCase();
        if (validModel.equals("carrera") || validModel.equals("commodore")) {
            // update the field or class var with the arg passed in
            // this. to refer to fields, differentiate from the param
            this.model = model;
        } else {
            this.model = "Unknown"; // cannot assign model number to the obj of type Car
                                    // if model != carrera or commodore
        }
    }
    
    public String getModel() {
        return this.model;
    }
}
```



### Constructors, get (accessor) & set (modifier) methods

- constructors'  purpose is to physically create the object from the class
  - is only called once at the start of the program
  - invoked when an object is created using the keyword `new`
  - no return type required
  - must have the same name as the class 
  - cannot be inherited by subclasses (need to declare in subclasses)
- constructors are used to set the initial parameters or initial values of the fields and other initialization you want to perform instead of inputting the values one by one using `.set` method
- constructors can be overloaded
  - call one of the constructors by inputting the relevant arguments
- can call another constructor w/i a constructor 
  - use `this()` in the constructor's first line where the parentheses include the relevant params
- IntelliJ shortcuts
  - `Alt + Insert` to generate code e.g. constructs, getters and setters automatically
  - alternatively, top bar `Code` - `Generate`
- Example of a good constructor:
  - the 1st constructor calls the 2nd, 
  - the 2nd calls the 3rd, 
  - the 3rd initialize the instance variables.
  - the 3rd constructor does all the work
  - no matter what constructor we all, the variables will always be initialized in the 3rd constructor
- Constructor chaining
  - the last constructor has  the "responsibility to initialize the variables"
  - reduce duplicated codes (and potential bugs)

<img src="C:\Users\jieqi\AppData\Roaming\Typora\typora-user-images\image-20210117120946479.png" alt="image-20210117120946479" style="zoom: 33%;" />

- `get` methods
  - fields are `private` so cannot be accessed/called in the Main class to get the relevant values 
  - need to use a publicly accessibly `getField` to get the values needed.
  - if there is no need for other classes to access the class fields, then getters are not needed 

```java
// Main class

public class Main {

    public static void main(String[] args) {
         BankAccount bankAccount = new BankAccount("12345", 0, "Bob", "bob@gmail.com", "8123");
         // BankAccount bankAccount = new BankAccount();
         // new BankAccount(); is actually calling the constructor (automatically), which is the special method that creates the class

        bankAccount.setAccountNum("46281234");
        bankAccount.setBalance(0);
        bankAccount.setCustomerName("Shar");
        bankAccount.setEmail("shar@gmail.com");
        bankAccount.setPhoneNum("81234567");

        System.out.println("Customer name: " + bankAccount.getCustomerName());
        bankAccount.depositFund(100);
        bankAccount.withdrawFund(10);
        bankAccount.withdrawFund(100);

        VipCustomer cus1 = new VipCustomer();
        System.out.println(cus1.getName()); // default name

        VipCustomer cus2 = new VipCustomer("Bob", 25000.00);
        System.out.println(cus2.getName()); // Bob

        VipCustomer cus3 = new VipCustomer("Tim", 100.00, "tim@gmail.com");
        System.out.println(cus3.getEmail()); // tim@gmail.com
    }
}

// BankAccount class
public class BankAccount {
    private String accountNum;
    private double balance;
    private String customerName;
    private String email;
    private String phoneNum;

    public BankAccount() {
        this("56789", 2.50, "Default name", "Default address", "Default phone");
        // if the empty constructor is called, we will set some default values for the 5 fields
        // (calling the other constructor)
        // needs to ensure that this is the first statement in the empty constructor block
        // i.e. this statement cannot be placed below sout

        System.out.println("Empty constructor is called");
    }
    // constructor can be overloaded
    // call one of the constructor by inputting the relevant arguments

    public BankAccount(String accountNum, double balance, String customerName, String email, String phoneNum) {
        System.out.println("Account constructor with parameters called");
        // setAccountNum(accountNum); // this could also work in some cases only
        this.accountNum = accountNum; // preferred
        this.balance = balance;
        this.customerName = customerName;
        this.email = email;
        this.phoneNum = phoneNum;
    }

    public BankAccount(String customerName, String email, String phoneNum) {
        this("99999", 10, customerName, email, phoneNum); // also works
        this.customerName = customerName;
        this.email = email;
        this.phoneNum = phoneNum;
    }

    public String getAccountNum() {
        return this.accountNum;
    }

    public double getBalance() {
        return this.balance;
    }

    public String getCustomerName() {
        return this.customerName;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPhoneNum() {
        return this.phoneNum;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void depositFund(double depositAmount) {
        if (depositAmount > 0) {
            this.balance += depositAmount;
            System.out.println("Deposit of $" + depositAmount + " made. \nCurrent balance = $" + this.balance);
        } else {
            System.out.println("Invalid input");
        }
    }

    public void withdrawFund(double withdrawAmount) {
        if (this.balance < withdrawAmount) {
            System.out.println("Only $" + this.balance + " available. \nWithdrawal not processed");
        } else {
            this.balance -= withdrawAmount;
            System.out.println("Withdrawal of $" + withdrawAmount + " made. \nCurrent balance = $" + this.balance);
        }
    }
}
```



### Inheritance

- OOP allows us to create class to inherit commonly used standard behaviour from other classes
  - e.g. Macbook and Dell PC are both PCs which share similar characteristics (inheritance from PCs) (e.g.  running of apps)
  - but they have different hardware/properties (e.g. MB runs on Mac OS while Dell runs on Win OS)
- IS-A r/ship
  - e.g. `ColouredCircle` IS-A `circle`, not HAS-A `circle`
- parent class can point to child class but not the converse

```java
Circle c2;
c2 = new Circle(p, 1);
c2 = new ColouredCircle(p, 1, "red"); // ok equivalently, Circle c = new ColouredCircle(p, 1, "red")

ColouredCircle c3 = new Circle(p, 1); // error: incompatible types

Circle c = new Circle(new Point(0,0), 4);
Object o = c; // compiler cannot be sure if this line will be executed, hence, do not refer to o as a cirlce
// o ==> Circle@6d00a15d
o.getArea(); // error, cannot access methods in Circle; compiler: "o is an object, getArea() is not in object ==> error"
Object o2 = new object();
// o2 ==> java.lang.Object@396a51ab
```

- use `extends` keyword - inherit from 
  - by doing this, we are able to use the state (fields) & behaviour (methods) that have been defined in the parent/base class (animal class in the example below)
  - in order to use the Animal class, we need to call the constructor of that class
- `super` keyword
  - to refer to the super class (the class that this Dog class is extending from, a.k.a. parent/base class)
  - this allows us to initialize the Animal class which this Dog class is part of 
  - but we can also add more features (fields, method) that are unique to the Dog class.
- `@Override`
  - create a version of that same method that exists in the parent class but make it unique for the subclass (child class)
  - i.e. create a method of the same name as the method from the parent class but we have overridden that with our own unique implementation
  - IntelliJ Shortcut: `alt + insert` shows the methods that are available in the Animal class that can be overridden
  - note: `java.lang.Object` -> extending this object class, which is a special/mother class created by Java, i.e. every class u create inherits from `java.lang.Object`

```java
// Dog Class
public class Dog extends Animal {
    private int eyes;
    private int legs;
    private int tail;
    private int teeth;
    private String coat;

    public Dog(String name, int size, int weight, int eyes, int legs, int tail, int teeth, String coat) {
        super(name, 1, 1, size, weight);
        this.eyes = eyes;
        this.legs = legs;
        this.tail = tail;
        this.teeth = teeth;
        this.coat = coat;
    }

    private void chew() {
        System.out.println("Dog.chew() called");
    }

    @Override
    public void eat() {
        System.out.println("Dog.eat() called");
        chew();
        super.eat(); // optional but is often included to initialize/perform basic functionality in that basic class
        // override the eat method in super class - Animal class (the equivalent in the base class that super refers to)
        
    }

    public void walk() {
        System.out.println("Dog.walk() called.");
        super.move(5);
        // super. refers to the move method in the super class
    }

    public void run() {
        System.out.println("Dog.run() called.");
        move(10);
        // does not have super., so this method finds whatever move method that it can find in this class first
        // if it cannot find move method in Dog class, it will find move method in Animal method
    }

    private void moveLegs(int speed) {
        System.out.println("Dog.moveLegs() called");
    }

    @Override
    public void move(int speed) {
        System.out.println("Dog.move() called");
        moveLegs(speed);
        super.move(speed);
    }
}

// Animal Class
public class Animal {

    private String name;
    private int brain;
    private int body;
    private int size;
    private int weight;

    public Animal(String name, int brain, int body, int size, int weight) {
        this.name = name;
        this.brain = brain;
        this.body = body;
        this.size = size;
        this.weight = weight;
    }

    public void eat() {
        System.out.println("Animal.eat() called");
    }

    public void move(int speed){
        System.out.println("Animal.move() called. Animal is moving at " + speed);
    }

    public String getName() {
        return name;
    }

    public int getBrain() {
        return brain;
    }

    public int getBody() {
        return body;
    }

    public int getSize() {
        return size;
    }

    public int getWeight() {
        return weight;
    }
}

// Main Class
public class Main {

    public static void main(String[] args) {
	// write your code here
        Animal animal = new Animal("Animal", 1, 1, 5, 5);

        Dog dog = new Dog("Yorkie", 8, 20, 2, 4, 1, 20, "long silky");
        dog.eat();
        // prints "Dog.eat() called", "Dog.chew() called", "Animal.eat() called"
        // in the order written in eat() method in the Dog class
        dog.walk();
        // prints
        // Dog.walk() called.
        // Animal.move() called. Animal is moving at 5

        dog.run();
        // prints
        // Dog.run() called.
        // Dog.move() called
        // Dog.moveLegs() called
        // Animal.move() called. Animal is moving at 10
    }
}
```

### this v.s. super keywords

- `this` - used to call the current class members (variables and methods)
  - required when we have a parameter with the same name as an instance variable (field) to differentiate the two
  - FOR CS2030S: recommended to use `this.` whenever we call the member variables

<img src="C:\Users\jieqi\AppData\Roaming\Typora\typora-user-images\image-20210116233120472.png" alt="image-20210116233120472" style="zoom: 33%;" />



- `super` - used to access/call the parent class members (variables and methods)
  - `super` is calling the method with the same name from the parent class.
  - w/o `super` keyword, in this case, it would be a recursive call, i.e. the method will call itself forever (or until memory is fully used)

<img src="C:\Users\jieqi\AppData\Roaming\Typora\typora-user-images\image-20210116233200056.png" alt="image-20210116233200056" style="zoom: 33%;" />

- Note: We can use both keywords in a class except static areas (the static block or a static method). Any attempt to do so will lead to compile-time errors.



### this() v.s. super() call

- `this()` - call a constructor from another overloaded constructor in the same class.
  - the call to `this()` can be used only in a constructor
  - `this()` MUST be the 1st statement in a constructor
  - it is used with constructor chaining (i.e. when one constructor calls another constructor), and helps to reduce duplicated code.
- `super()` - the only way to call a parent constructor
  -  `super()` MUST be the 1st statement in each constructor
  - the Java Compiler puts a default call to `super()` even if we don't add it
  - it is always the no-arg `super` which is inserted by compiler (constructor w/o arguments)
  - Even Abstract classes have constructors, although you can never instantiate an abstract class using the `new` keyword.
  - an abstract class is still a `super` class, so its constructors run when someone makes an instance of a concrete subclass.
- Note: a constructor can have a call to super() or this() but **NEVER** both

<img src="C:\Users\jieqi\AppData\Roaming\Typora\typora-user-images\image-20210117121035281.png" alt="image-20210117121035281" style="zoom: 33%;" />



### Method Overloading v.s. Method Overriding

<u>Method Overloading</u>

- providing 2 or more separate methods in a class with the same name but different parameters
- method return type may or may not be different 
- allows us to reuse the same method name
  - reduces duplicated code
- overloading does not have anything to do with polymorphism but Java developers often refer to overloading as Compile Time Polymorphism
  - i.e. the compile decided which method is going to be called based on the method name, return type and argument list
- static and instance methods can be overloaded
- usually overloading happens inside a single class, but a method can also be treated as overloaded in the subclass of that class.
  - a subclass inherits one version of the method from the parent class and then the subclass can have another overloaded version of that method
- **Methods will be considered overloaded if both follow these rules:**
  - Methods must have the same name
  - Methods must have different parameters
- **Overloaded methods may or may not:**
  - have different return types
  - have different access modifiers
  - thrown different checked or unchecked exceptions



<u>Method Overriding</u> 

- defining a method in a child class (subclass) that already exists in the parent class with the same signature (same name & arguments)
- by extending the parent class, the child class gets all the methods defined in the parent class (those methods  are aka derived methods)
- a.k.a. Runtime Polymorphism / Dynamic Method Dispatch
  - the method that is going to be called is decided at runtime by the JVM
- Recommended to put `@Override` immediately above the method definition.
  - this is an annotation that the compile reads and will then show us an error if overriding rules are not followed correctly
- Only **instance methods can be overridden** but not static methods
- Method will be considered overridden if we follow these rules:
  - it must have the same name and same arguments
  - return type can be a subclass of the return type in the parent class
  - it cannot have a lower access modifier
    - if the parent method has `protected`  access modifier, then the overriding method in child class cannot have `private` but can have `public` access modifier
- Important notes:
  - only inherited methods can be overridden
    - methods can be overridden only in child classes
  - constructors and private methods cannot be overridden
  - methods that are final cannot be overridden
  - a subclass can use` super.methodName()` to call the superclass version of an overridden method

<img src="C:\Users\jieqi\AppData\Roaming\Typora\typora-user-images\image-20210117122926450.png" alt="image-20210117122926450" style="zoom: 33%;" />

|                      | Method Overloading                                           | Method Overriding                                            |
| -------------------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| **Aim**              | Provided functionality to reuse a method name with different parameters | Used to override a behaviour which the class has inherited from the parent class |
| **Class**            | Usually in a single class but may also be used in a child class | Always in 2 classes that have a child-parent or IS-A r/ship  |
| **Params**           | Must have the same name but different parameters             | Must have the same name and same parameters                  |
| **Return Types**     | May have different return types                              | Must have the same return type or covariant return type (a return type that is in the parent-child r/ship) (child class) |
| **Access Modifiers** | May have different access modifiers (private, protected, public) | Must NOT have a lower modifier but may have a higher modifier |
| **Exceptions**       | May throw different exceptions                               | Must NOT throw a new or broader checked exception            |

*Covariant Return Type*

<img src="C:\Users\jieqi\AppData\Roaming\Typora\typora-user-images\image-20210117124515023.png" alt="image-20210117124515023" style="zoom: 25%;" />

- return types are different (covariant return type)
- the `Burger` class is the parent class and `HealthyBurger` is a child class
- the method `createBurger` in the class `HealthyBurgerFactory` can return any child type of burger 
  - in this case, we only have 1 child class `HealthyBurger`  



### Static v.s. Instance Methods

<u>Static methods</u> (a.k.a. class methods)

- declared using a `static` modifier

- can be called w/o creating an object of class
  - designed with the aim to be shared among all objects created from the same class
  - they are referenced by the class name itself or reference to the object of that class
  - cannot be overridden, can be overloaded
  
- static methods cannot access instance methods and instance variables directly 

- usually used for operations that do not require any data from an instance of the class (from `this.`)
  - `this` keyword is the current instance of a class
  - cannot use `this` keyword in static methods
  
- normal class fields require an instance of the class that doesn't actually exist until an instance has been created -> static method cannot access non-static fields (which don't exist when the static methods are called)

- A static method can still access non-static fields and methods in another class because it creates an instance of a class in order to do so.
  
  - The restriction is purely on static method accessing non-static methods and fields in its own class.
  
- methods that does not use instance variables should be declared as a static method
  
  - e.g. main is a static method and it is called by the JVM when it starts an application
  
- When to use?
  
  - when you have code that can be shared across all instances of the same class 
  - factory methods
  - methods that access/mutate static fields
  - no overriding as static methods are resolved at compile time
  - static block to initialize static fields that cannot be done via =
  
  ```java
  // factory
  static Circle getCircle(Point centre, double radius) {
  	return new Circle(centre, radius); // constructor will be declared as private in this case for better abstraction
  }
  
  // access/mutate static fields
  static int getNumOfCircles() {
      return Circle.numOfCircles;
  }
  
  // initialisation
  class MyColors {
      static List<Color> colors = new ArrayList<>();
      static {
          colors.add(Colors.BLUE);
      }
  }
  ```
  
  

<img src="C:\Users\jieqi\AppData\Roaming\Typora\typora-user-images\image-20210117131754132.png" alt="image-20210117131754132" style="zoom: 33%;" />



<u>Instance methods</u>

- belong to an instance (object) of a class (not the class itself)
  - can only be called after creating the object of the class
  - can be overridden & overloaded
- to use an instance method, we need to instantiate the class first, usually by using the `new` keyword
- instance methods can access instance methods and instance variables directly
- instance methods can also access static methods an static variables directly
- instance methods are created more often than static methods

<img src="C:\Users\jieqi\AppData\Roaming\Typora\typora-user-images\image-20210117132335102.png" alt="image-20210117132335102" style="zoom:25%;" />

<img src="C:\Users\jieqi\AppData\Roaming\Typora\typora-user-images\image-20210117132446770.png" alt="image-20210117132446770" style="zoom:25%;" />

| **Points**                        | **Static method** (Class method)                             | **Non-static / Instance method**                             |
| :-------------------------------- | :----------------------------------------------------------- | :----------------------------------------------------------- |
| **Definition**                    | A **static method** is a method that belongs to a class, but it does not belong to an instance of that class and this method can be called without the instance or object of that class. | Every methods in Java defaults to non-static method without **static** keyword preceding it. **non-static** methods can access any **static** method and **static** variable also, without using the object of the class. |
| **Accessing members and methods** | In **static** method, the method can only access only static data members and static methods of another class or same class. Static methods cannot access instance methods and instance variables directly. They must use reference to object. (`object.method/variable`) | In **non-static** method, the method can access static data members and static methods as well as non-static members and method of another class or same class. |
| **Binding process**               | **Static** method uses compile time or early binding.        | **Non-static** method uses runtime or dynamic binding.       |
| **Overriding**                    | **Static** method cannot be overridden because of early binding. | **Non-static** method can be overridden because of runtime binding. |
| **Memory allocation**             | In **static** method, less memory is use for execution because memory allocation happens only once, because the static keyword fixed a particular memory for that method in ram. . | In **non-static** method, much memory is used for execution because here memory allocation happens when the method is invoked and the memory is allocated every time when the method is called. |



### Static Variables v.s. Instance Variables

<u>Static Variables</u> (a.k.a. static member/class variables)

- declared by using the keyword `static`

- When a variable is declared as static, then a single copy of the variable is created and shared among all objects at a class level. 
  - associated with the class itself, rather than the instances of the class
  - FOR CS2030S, always use `Class.staticVar` to access static variables
  
- Static variables are, essentially, global variables. 

- All instances of the class share the same static variable.

- if changes are made to that static variable, all other instances will see the effect of those change.

- When to use?

  - for aggregated data 
  - for constants e.g. `private static final double PI = 3.146`

  ```java
  class Circle {
      private final Point centre;
      private final double radius;
      private static int numOfCircles = 0; // mutatble
      
      private Circle(Point centre, double radius) {
          this.centre = centre;
          this.radius = radius
          Circle.numOfCircles++;
      }
  }
  ```

<img src="C:\Users\jieqi\AppData\Roaming\Typora\typora-user-images\image-20210117144011395.png" alt="image-20210117144011395" style="zoom:33%;" />

- static variables are not used very often but can sometimes be very useful
  - e.g. when reading user input using `Scanner`, we will declare scanner as a static variable
  - so that static methods can access it directly
- note: 
  - can only create static variables at class-level
  - In Java, a static variable is a class variable (for whole class). So if we have static local variable (a variable with scope limited to function), it violates the purpose of static. Hence compiler does not allow static local variable.
  - static block and static variables are executed in order they are present in a program. (see e.g. below)

```java
// Java program to demonstrate execution 
// of static blocks and variables 
class Test { 
   // static variable 
   static int a = m1(); 
 
   // static block 
   static { 
     System.out.println("Inside static block"); 
   } 
 
   // static method 
   static int m1() { 
     System.out.println("from m1"); 
     return 20 ; 
   } 
 
   // static method(main !!) 
   public static void main(String[] args) { 
     System.out.println("Value of a : " + a); 
     System.out.println("from main"); 
   } 
} 

// output
// from m1
// Inside static block
// Value of a : 20
// from main
```

*Non-static Variables*

1. local variables 
   - local to the block that creates it, e.g. function/method, 
   - i.e. only can be accessed within the block that created it) 
2. instance variables



<u>Instance Variable</u> (a.k.a. fields or member variables)

- Instance variables are non-static variables and are declared in a class outside any method, constructor or block.
- As instance variables are declared in a class, these variables are created when an object of the class is created and destroyed when the object is destroyed.
  - Instance Variable can be accessed only by creating objects.
  - instance  variables belong to an instance (object) of a class
  - instance variables represent the state of an instance
  - every instance has it's own copy of an instance variable
  - every instance can have a different value (state)
- Unlike local variables, we may use access specifiers for instance variables. If we do not specify any access specifier then the default access specifier will be used.
- Initialisation of Instance Variable is not Mandatory. Its default value is 0.
- do not use `static` keyword

<img src="C:\Users\jieqi\AppData\Roaming\Typora\typora-user-images\image-20210117144338991.png" alt="image-20210117144338991" style="zoom:33%;" />

| Static variable (Class variable)                             | Non-static variable                                          |
| :----------------------------------------------------------- | :----------------------------------------------------------- |
| Static variables can be accessed using class name            | Non-static variables can be accessed using instance of a class |
| Static variables can be accessed by static and non static methods | Non-static variables cannot be accessed inside a static method. |
| Static variables reduce the amount of memory used by a program. | Non static variables do not reduce the amount of memory used by a program |
| Static variables are shared among all instances of a class.  | Non static variables are specific to that instance of a class. |
| Static variable is like a global variable and is available to all methods. | Non static variable is like a local variable and they can be accessed through only instance of a class. |

### Static Initialization 

- static initialization blocks are called before any non-static methods, including the constructor

```java
public class SIBTest {
    public static final String owner;

    static {
        owner = "shar";
        System.out.println("SIBTest static initialization block called");
    }

    public SIBTest() {
        System.out.println("SIB constructor called");
    }

    static {
        System.out.println("2nd initialization block called");
    }

    public void someMethod() {
        System.out.println("someMethod called");
    }
    
    public static void main(String[] args) {
        SIBTest test = new SIBTest();
        test.someMethod();
        System.out.println("Owner is "  + SIBTest.owner);
    }
}
/* output:
SIBTest static initialization block called
2nd initialization block called
SIB constructor called
someMethod called
Owner is shar
*/
```



### Composition

- inheritance only allows one class to inherit from another one but not multiple classes
- composition can overcome this issue - creating an object in another object
  - e.g. each instance created in the following PC example can have `Case`, `Monitor` and `Motherboard`
  - HAS-A r/ship
- Aliasing
  - sharing of references 
  - e.g. `Point p = new Point(0,0); Circle c1 = new Circle(p, 1); Circle c2 = new Circle(p, 4);` 
  - changing `p` changes both circles
  - to avoid the drawback of aliasing -> declare diff points (problem: too many things)

```java
// PC Class
public class PC {
    private Case theCase;
    private Monitor monitor;
    private Motherboard motherboard;

    public PC(Case theCase, Monitor monitor, Motherboard motherboard) {
        this.theCase = theCase;
        this.monitor = monitor;
        this.motherboard = motherboard;
    }

    public void powerUp() {
        theCase.pressPowerButton();
        drawLogo();
    }

    private void drawLogo() {
        // Fancy graphics
        monitor.drawPixelAt(1200, 50, "yellow");
        // no need getters here. if we needed to check whether the case (object) has been initialized,
        // or look for some other forms of validation, then can leave the getters there
    }

//    private Case getTheCase() {
//        return theCase;
//    }
//
//    private Monitor getMonitor() {
//        return monitor;
//    }
//
//    private Motherboard getMotherboard() {
//        return motherboard;
//    }
}

// Monitor class
public class Monitor {
    private String model;
    private String manufacturer;
    private int size;
    private Resolution nativeResolution; // Resolution class is part of Monitor
                                        // Resolution is a component of a monitor (composition)

    public Monitor(String model, String manufacturer, int size, Resolution nativeResolution) {
        this.model = model;
        this.manufacturer = manufacturer;
        this.size = size;
        this.nativeResolution = nativeResolution;
    }

    public void drawPixelAt(int x, int y, String color) {
        System.out.println("Drawing pixel at (" + x + ", " + y + ") in colour " + color);
    }

    public String getModel() {
        return model;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public int getSize() {
        return size;
    }

    public Resolution getNativeResolution() {
        return nativeResolution;
    }
}
//Motherboard class
public class Motherboard {
    private String model;
    private String manufacturer;
    private int ramSlots;
    private int cardSlots;
    private String bios;

    public Motherboard(String model, String manufacturer, int ramSlots, int cardSlots, String bios) {
        this.model = model;
        this.manufacturer = manufacturer;
        this.ramSlots = ramSlots;
        this.cardSlots = cardSlots;
        this.bios = bios;
    }

    public void loadProgram(String programName) {
        System.out.println("Program " + programName + " is now loading...");
    }

    public String getModel() {
        return model;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public int getRamSlots() {
        return ramSlots;
    }

    public int getCardSlots() {
        return cardSlots;
    }

    public String getBios() {
        return bios;
    }
}
// Case class
public class Case {
    private String model;
    private String manufacturer;
    private String powerSupply;
    private Dimensions dimensions;

    public Case(String model, String manufacturer, String powerSupply, Dimensions dimensions) {
        this.model = model;
        this.manufacturer = manufacturer;
        this.powerSupply = powerSupply;
        this.dimensions = dimensions;
    }

    public void pressPowerButton() {
        System.out.println("Power button pressed.");
    }

    public String getModel() {
        return model;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getPowerSupply() {
        return powerSupply;
    }

    public Dimensions getDimensions() {
        return dimensions;
    }
}
// Dimensions class
public class Dimensions {
    private int width;
    private int height;
    private int depth;

    public Dimensions(int width, int height, int depth) {
        this.width = width;
        this.height = height;
        this.depth = depth;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getDepth() {
        return depth;
    }
}

// Main class
public class Main {

    public static void main(String[] args) {
	    Dimensions dimensions = new Dimensions(20, 20, 5);
        Case theCase = new Case("220B", "Dell", "240", dimensions);

        // creates an instance of a class w/o using a variable
        Monitor theMonitor = new Monitor("27in Beast", "Acer", 27, new Resolution(2540, 1440));

        Motherboard theMotherBoard = new Motherboard("BJ-200", "Asus", 4, 6, "v2.44");

        PC thePC = new PC(theCase, theMonitor, theMotherBoard);

//        thePC.getMonitor().drawPixelAt(1500, 1200, "red"); 
        // accessing drawPixelAt via Monitor
//        thePC.getMotherboard().loadProgram("Windows 1.0");
//        thePC.getTheCase();

        thePC.powerUp(); // access theCase, drawLogo -> monitor
}
// Output
// Power button pressed.
// Drawing pixel at (1200, 50) in colour yellow
```



### Encapsulation

- the mechanism that allows you to restrict access to certain components in the objects that you are creating.
- this allows the implementer of the program to protect the members of a class from external access in order to guard against unauthorized access.
  - allow the implementer to change things without breaking code elsewhere

```java
// Example w/o Encapsulation
// Main Class
public class Main {

    public static void main(String[] args) {

        Player player = new Player();
        player.name = "Shar";
        player.health = 20;
        // 3RD PROBLEM
        // might forget to initialize some of the fields
        player.weapon = "Sword";
        // no constructor was used, so need to initilize the fields one by one;

        player.loseHealth(10);
        System.out.println("Remaining health = " + player.healthRemaining());

        player.health = 200;
        player.loseHealth(11);
        // 1ST PROBLEM
        // calling the player class is able to change those fields directly
        // by being able to access those fields directly,
        // you're potentially opening up your app to be access in ways that you didnt want it to do
        // one solution is to use a constructor

        System.out.println("Remaining health = " + player.healthRemaining());

        EnhancedPlayer enhancedPlayer = new EnhancedPlayer("Shar", 200, "Sword");
        // if health is out of the valid range --> assign default value
        // if health is w/i the range --> assign the arg value
        System.out.println("Initial health is " + enhancedPlayer.getHealth());
    }

// Player Class
public class Player {
    public String name;
    // 2ND PROBLEM
    // if changed to public String fullName,
    // it will result in bugs as the Main class cannot find the initial name field.
    // becomes a huge problem in large apps

    public int health;
    public String weapon;

    public void loseHealth(int damage) {
        this.health = this.health - damage;
        if (this.health <= 0) {
            System.out.println("Player knocked out");
            // reduce the no. of live remaining for the player;
        }
    }

    public int healthRemaining() {
        return this.health;
    }
}

// Example w Encapsultion
public class EnhancedPlayer {
    private String name;
    private int hitPoints = 100; // can change the name of the fields Shift + Fn + F6; right-click - refactor - rename
    private String weapon;

    public EnhancedPlayer(String name, int health, String weapon) {
        this.name = name;

        if (health > 0 && health <= 100) {
            this.hitPoints = health;
        }

        this.weapon = weapon;
    }

    public void loseHealth(int damage) {
        this.hitPoints = this.hitPoints - damage;
        if (this.hitPoints <= 0) {
            System.out.println("Player knocked out");
        }
    }

    public int getHealth() {
        return hitPoints;
    }
    // health is assigned private so cannot access this value in Main class
    // hence, need to use a getter, which is publicly accessible to get the health value
}
```



### Polymorphism

- allows actions to act differently based on the actual object that the action is performed on 
- for similar/repeated subclasses with minor changes, can right-click `refactor - copy class` to duplicate
  - `refactor - move - make inner class of ` - move subclass to Main class
  - less recommended method `refactor - inline super class` (initialize at the moment of creation) 
- `getClass().getSimpleName()` - get the name of the class this is called under

```java
class Movie {
    private String name;

    public Movie(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String plot() {
        return "no plot here";
    }
}

class Jaws extends Movie {
    public Jaws() {
        super("Jaws");
    }
    
	@Override
    public String plot() {
        return "A shark eats lots of people";
    }
}

class IndependenceDay extends Movie{
    public IndependenceDay() {
        super("Independence Day");
    }

    @Override
    public String plot() {
        return "Aliens attempt to take over planet Earth";
    }
}

class MazeRunner extends Movie {
    public MazeRunner() {
        super("Maze Runner");
    }

    @Override
    public String plot() {
        return "Kids try to escape a maze";
    }

}

class StarWars extends Movie{
    public StarWars() {
        super("Star Wars");
    }

    @Override
    public String plot() {
        return "Imperial Forces try to take over the universe";
    }
}

class Forgettable extends Movie{
    public Forgettable() {
        super("Forgettable");
    }
    // no plot method
}

public class Main {

    public static void main(String[] args) {
	    for (int i = 1; i < 11; i++) {
	        Movie movie = randomMovie();
            System.out.println("Movie #" + i + ": " + movie.getName() + "\n" +
                    "Plot: " + movie.plot() + "\n");
        }

    }

    public static Movie randomMovie() {
        int randomNum  = (int) (Math.random() * 5 + 1); // math.random return random double between 0 and 1
        System.out.println("Random number generated was:  " + randomNum);
        switch (randomNum) {
            case 1:
                return new Jaws();
                // dont need break here

            case 2:
                return new IndependenceDay();

            case 3:
                return new StarWars();

            case 4:
                return new MazeRunner();

            case 5:
                return new Forgettable();

                // could put null for default case instead of return null after switch;
        }
        return null;
    }
}

/*
Random number generated was:  2
Movie #2: Independence Day
Plot: Aliens attempt to take over planet Earth
// if the class has overridden plot method, the corresponding plot will be displayed -> unique functionality

Random number generated was:  5
Movie #4: Forgettable
Plot: no plot here
// if the class has no overridden plot method, the super equivalent will be displayed
 */

```



### Arrays

<u>Declaration</u>

- `type[] varName` or  `type varName[];` 
  - e.g. `int[] intArray`  ; `MyClass[] myClassArr ` ; `Object[] objectArr`

<u>Instantiation</u>

- `varName = new type[size]`
- The elements in the array allocated by *new* will automatically be initialized to 
  - **zero** (for numeric types), 
  - **false** (for boolean), or 
  - **null** (for reference types)

```java
int intArray[];    //declaring array
intArray = new int[20];  // allocating memory to array

// equivalently
int[] intArray = new int[20]; // combining both statements in one
intArray[0] = 10; 

// Declaring array literal
 int[] intArray = new int[]{1,2,3,4,5,6,7,8,9,10 }; 
// no. of elements in {...} is the length of the arr
int[] intArray = {1,2,3,4,5,6,7,8,9,10 }; 
// no need to write new int[] for this type of instantiation 
// but need to write new int[] if redirecting the array
intArray = new int[] {5,4,3,2,1};
```

<u>Accessing Java Array Elements using for Loop</u>

```java
// accessing the elements of the specified array
for (int i = 0; i < arrName.length; i++) {
  System.out.println("Element at index " + i + " : "+ arr[i]);
}
```

<u>Copy Array</u>

```java
int[] sortedArray = Arrays.copyOf(array, array.length);
// to String
Arrays.toString(arr);
```

Reference Type v.s. Value Types

- value types (for primitives): store the value directly

<img src="C:\Users\jieqi\AppData\Roaming\Typora\typora-user-images\image-20210119133440942.png" alt="image-20210119133440942" style="zoom:33%;" />



<img src="C:\Users\jieqi\AppData\Roaming\Typora\typora-user-images\image-20210119133513342.png" alt="image-20210119133513342" style="zoom:33%;" />



```java
import java.util.Arrays;

public class Reference {
    public static void main(String[] args) {
        int[] myIntArray = new int[5];
        int[] anotherArray = myIntArray;

        System.out.println("myIntArray = " + Arrays.toString(myIntArray));
        System.out.println("anotherArray = " + Arrays.toString(anotherArray));
        
        System.out.println("myIntArray after modify = " + Arrays.toString(myIntArray));
        System.out.println("anotherArray after modify = " + Arrays.toString(anotherArray));
    }

    public static void modifyArray(int[] arr) {
        arr[0] = 2;
        arr = new int[] {1,2,3,4,5};
    }
}

/* 
myIntArray = [0, 0, 0, 0, 0]
anotherArray = [0, 0, 0, 0, 0]
myIntArray after modify = [2, 0, 0, 0, 0]
anotherArray after modify = [2, 0, 0, 0, 0]
 */
// parameter reference (the one that has the name arr) points to a different array
// once we used an equal sign, we are effectively dereferencing that original reference.
// a new arr is created in the memory that arr points to
// `myIntArray` and `anotherArray` is still pointing to the original array
```

### Array Lists

https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/ArrayList.html or `ctrl + click on .method`

- can only use on class, not primitives

```java
ArrayList<String> //ok
ArrayList<int> //error
// can create a class for storing int to be used
```



### Linked Lists

- each element in the list holds a link to the item that follows it, as well as the actual value you want to store.
- reduces memory and time consumption
  - in the following e.g. for removal of elements, since nth is pointing to `Perth` anymore, `Perth` will be destroyed 

<img src="C:\Users\jieqi\AppData\Roaming\Typora\typora-user-images\image-20210120223721615.png" alt="image-20210120223721615" style="zoom:33%;" /><img src="C:\Users\jieqi\AppData\Roaming\Typora\typora-user-images\image-20210120223624139.png" alt="image-20210120223624139" style="zoom:33%;" />



### Interface

- an interface specifies methods that a particular class which implements the interface must implement
  - i.e. a class that `implements` an interface needs to have all the methods written in the interface
- Interfaces are used to achieve abstraction and hence security
- Java does not support "multiple inheritance" (a class can only inherit from one superclass). However, it can be achieved with interfaces, because the class can **implement** multiple interfaces. 
  - **Note:** To implement multiple interfaces, separate them with a comma
- If the methods used in a class is changed, it could lead to many required changes and potential bugs for other codes that use these methods. Using and sticking to interfaces guarantees that the functionality of the program will not change (even if code change?)
- interfaces exist to define the methods that must be implemented
  - access  modifiers (e.g. `public`) are unnecessary: will be done in actual classes, redundant in interfaces
  - **CAN-DO or MUST-DO relationship**
- interface is there at an abstract level to tell you what methods are valid and have to be actually overridden in an actual class. --> cannot instantiate it using `new`
  - have to use the actual class that has overridden that functionality.
- **Abstract class:** is a restricted class that cannot be used to create objects (to access it, it must be inherited from another class).
- **Abstract method:** can only be used in an abstract class, and it does not have a body. The body is provided by the subclass (inherited from).
- Like **abstract classes**, interfaces **cannot** be used to create objects
- Interface methods do not have a body - the body is provided by the "implement" class
- On implementation of an interface, you must override all of its methods
- Interface methods are by default `abstract` and `public`
- Interface attributes are by default `public`, `static` and `final`
- An interface cannot contain a constructor (as it cannot be used to create objects)
- when to inherit from another class or to implement an interface
- consider the relationship of the final class to the object that it's extending or implementing. 
  - With our telephone example both devices have a telephone. The desk phone doesn't have anything else. But the mobile phone is not just a phone, it's more accurate to say that a mobile phone is actually a computer that has a phone interface. So you can say that a desk phone can telephone and a mobile phone can telephone, which indicates that they should both implement a phone interface rather than extending a phone class.
  - e.g. a dog is an animal and a bird is an animal. So they both inherit from the animal class. But a dog can walk so it implements walk. And a bird can both walk and fly so that would implement from both interfaces.



### Inner Classes (a.k.a. nested classes)

- defined inside the scope of another class
- achieve encapsulation: Sometimes you will need to program a class in such a way so that no other class can access it. Hence it would be better if you enclose it within other classes.
  - i.e. private inner classes

<u>**4 types:**</u>

1. **Non-static nested inner class** (a.k.a. inner classes)

   - involves the nesting of a class inside another class. 
   - The inner class can access the all the fields and methods, even private variables, of the outer class.
   - can modify access to the inner class by using access modifier keywords(e.g. private, protected, and default)
   - Similarly, access specifiers can help nest interfaces inside one another.
   - NOTE: even though we can have nested static classes, we **cannot have methods that are static inside the nested classes**. Because all the methods of the nested class are implicitly connected to the object of its outer enclosing class. Hence they cannot declare any static methods for themselves.

   ```java
   class OuterClass {
     public class InnerClass { // usually inner classes will be declared as private
       public void print() {
         System.out.println("I am printing from the inner class!");
       }
     }
   }
   
   public class NestedInnerClass {
     public static void main(String[] args) {
       OuterClass.InnerClass in = new OuterClass().new InnerClass(); 
       in.print();
     }
   }
   // I am printing from the inner class!
   ```

   

2. **Static nested classes**

   - If you declare the inner class to be static, then you can access the class **without having to create an object of the outer class.** 

   - However, the **static class will not have access to members of the outer class**. 

   - We can access the elements of the outer class from the inner class.

     ```java
     class OuterClass {
       public static class InnerClass {
         public void print() {
           System.out.println("I am printing from a static inner class!");
         }
       }
     }
     
     public class StaticInnerClass {
       public static void main(String[] args) {
         OuterClass.InnerClass in = new OuterClass.InnerClass(); 
         in.print();
       }
     }
     // I am printing from a static inner class!
     ------------------------------------------
   public class OuterClass {
         static int x;
         
         static int foo() {
             return 0;
         }
     
         int bar() {
             return 1;
         }
     
         static class StaticB {
     
         }
     
         public static void main(String[] args) {
             x = 1; // initialising the static variable
             OuterClass a = new OuterClass();
             StaticB b = new staticB();
             new OuterClass().foo(); // outer class is not a static class so has to create an instance first, alternatively, this is equivalent to
             new OuterClass();
             OuterClass.foo;
                 
                 
             new OuterClass().bar();
             // all can compile
         }
     }
     ```
     
     

3. **Method local inner classes** (less often used)

   - the outer class method contains the inner class.

   ```java
   class OuterClass {
     void outerClassMethod() {
       final String site = "Data-Flair.training";
         
       System.out.println("Hey I am inside outerClassMethod");
         
       class InnerClass {
         void innerClassMethod() {
           System.out.println("I am studying Java at " + site);
         }
       }
         
       InnerClass in = new InnerClass(); 
       in.innerClassMethod();
     }
   }
   
   public class MainClass {
     public static void main(String[] args) {
       OuterClass out = new OuterClass();
       out.outerClassMethod();
     }
   }
   /*
   Hey I am inside outerClassMethod
   I am studying Java at Data-Flair.training
   */
   ```

   

4. **Anonymous inner classes**

   - inner classes without a name.
   - need to be declared and instantiated at the same time
   - The definition of the classes are written outside the scope of the outer class. 
   - These classes are useful when we have to design an interface or overload a method. 
   - It saves the effort of us having to nest the class.
   - 2 types of anon inner classes: subclass of the specified type & the implementer of the specified interface

   <u>Subclass of the specified type</u>

   - the anonymous class is put inside a subclass of the outer class.

   ```java
   class OuterClass {
     void print() {
       System.out.println("I am in the print method of superclass");
     }
   }
   
   class AnonymousClass {
     // An anonymous class with OuterClass as base class 
     // start of the anonymous class.
     static OuterClass out = new OuterClass() {
       void print() {
         super.print();
         System.out.println("I am in Anonymous class");
       }
     };
       
     public static void main(String[] args) {
       out.print();
     }
   }
   /*
   I am in the print method of the superclass
   I am in Anonymous class
   */
   ```

   <u>Implementer of specified interface</u>

   - The anonymous class can extend a class or implement an interface at a time. 

   ```java
   interface Anonym {
     void print();
   }
   
   class AnonymousClass {
     // An anonymous class with OuterClass as base class 
     // start of the anonymous class.
     static Anonym an = new Anonym() {
       public void print() {
         System.out.println("I am an implementation of interface Anonym");
       }
     };
       
     public static void main(String[] args) {
       an.print();
     }
   }
   // I am an implementation of interface Anonym
   ```

   

   

### Abstract Classes

- abstraction is when you define the required functionality for something without actually implementing the data house.
- In other words we're focusing on what needs to be done, not on the how it's to be done.
- not all methods needs to be overridden (as compared to interface where all methods have to be overridden)
- cannot directly instantiate an abstract class -> create another class that extends the abstract class before instantiation

```java
public abstract class Animal {
    private String name;

    public Animal(String name) {
        this.name = name;
    }

    public abstract void eat();
    public abstract void breathe();
    // all animals can breathe and eat --> better to include them in the base class instead of interfaces

    public String getName() {
        return this.name;
    }
}

public interface CanFly {
        void fly();
}

public abstract class Bird extends Animal implements CanFly {
    // Animal class contains field name, methods eat() and breathe();
    public Bird(String name) {
        super(name);
    }

    @Override
    public void eat() {
        System.out.println(getName() + " is pecking");
    }

    @Override
    public void breathe() {
        System.out.println("Breathe in, breathe out, repeat");
    }
    
	// abstract method version
    public abstract void fly(); 
    // not all birds can fly
    // better to make bird an abstract class that extends the Animal class
    // make fly an abstract method that has to be overridden (for birds that can fly)
    // can be replaced with an interface CanFly to be more flexible, there are animals that can fly but are not birds (e.g. bats which are mammals)
    
    // interface version  
    @Override
    public void fly() {
        System.out.println(getName() + " is flapping its wings");
    }

    
}

public class Parrot extends Bird {
    public Parrot(String name) {
        super(name);
    }

    // automatically inherits the eat() and breathe() methods in the bird class
    @Override
    public void fly() {
        System.out.println("Flitting from branch to branch");
    }
}

public class Main {
    public static void main(String[] args) {
        // Bird bird = new Bird("Parrot");
        // compilation error: cannot directly instantiate an abstract class
        Parrot parrot = new Parrot("Australian Ringneck");
        parrot.breathe();
        parrot.eat();
        parrot.fly();
    }
}

/*
Breathe in, breathe out, repeat
Australian Ringneck is pecking
Flitting from branch to branch
*/

public class Penguin extends Bird {
    public Penguin(String name) {
        super(name);
    }

    @Override
    public void fly() {
        super.fly(); // got wings that can flap, call parent's methods
        System.out.println("I am not very good at flying, can I go for swimming instead?");
    }
}
/*
Emperor is flapping its wings
I am not very good at flying, can I go for swimming instead?
*/
```

- A constructor of abstract class is called when an instance of a inherited class is created

```java
// An abstract class with constructor 
abstract class Base { 
    Base() { 
        System.out.println("Base Constructor Called"); 
    } 
    abstract void fun(); 
} 

class Derived extends Base { 
    Derived() { System.out.println("Derived Constructor Called"); } 
    void fun() { System.out.println("Derived fun() called"); } 
} 

class Main { 
    public static void main(String args[]) {  
       Derived d = new Derived(); 
    } 
} 
/*
Base Constructor Called
Derived Constructor Called
*/
```

- we can have an abstract class without any abstract method. This allows us to create classes that cannot be instantiated, but can only be inherited.

```java
// An abstract class without any abstract method 
abstract class Base {    
    void fun() { System.out.println("Base fun() called"); } 
} 
   
class Derived extends Base { 
    // empty
} 
   
class Main { 
    public static void main(String args[]) {  
        Derived d = new Derived(); 
        d.fun(); 
    } 
} 
// Base fun() called
```

- Abstract classes can also have final methods (methods that cannot be overridden)

```java
// An abstract class with a final method 
abstract class Base { 
    final void fun() { System.out.println("Derived fun() called"); } 
} 
   
class Derived extends Base {} 
   
class Main { 
    public static void main(String args[]) {  
       Base b = new Derived(); 
       b.fun(); 
    } 
}  
// Derived fun() called
```



### Interfaces v.s. Abstract Classes

- both are used to achieve abstraction

|                                   | Interfaces                                                   | Abstract Classes                                             |
| --------------------------------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| Function / Purpose                | - Declaration of methods of an class, not implementation <br /><br />- (define what 'child classes' must do) | to provide a common definition of a base class that multiple derived classes can share |
| **Type of Methods**               | [CS2030S]: can only have **public and abstract** methods <br /><br />(for Java 8 and above, can have default and private non-abstract methods -> impure interfaces) | can have abstract (at least one) and non-abstract (concreate) methods (can also have static and default methods) |
| **Type of Variables**             | only **static and final** (by default) variables <br /><br />(non-static variables require instantiation, but u cannot instantiate an interface) | can have **final, non-final, static and non-static** variables; <br /><br />can have member variables that are inherited |
| **Accessibility of Data Members** | members of in interface are **public** by default            | members of abstract classes can have different access modifiers (e.g. **private, protected** etc) |
| **Constructors**                  | NA (cannot instantiate an interface)                         | Yes                                                          |
| **Implementation**                | cannot provide the implementation for abstraction classes (empty methods, purely abstract) | can provide the implementation of interface                  |
| **Inheritance v.s. Abstraction**  | `implements`                                                 | `extends`                                                    |
| **Multiple implementation**       | an interface can extend one other interface only             | can extend only one parent class and implement multiple interfaces |
| **Subclasses**                    | can implement multiple interfaces                            | - can extend only 1 abstract class; <br /><br />- the subclass provides the implementations for all of the abstract methods in its parent (abstract) classes, <br /><br />- if it does not, then the subclass must also be declared as abstract |
| **When to use**                   | expect unrelated class to implement the interface (e.g. Comparable is implemented by many unrelated classes) | classes that extend an abstract class have many common methods or fields, or require access modifiers other than public (such as protected and private). |
|                                   | You want to specify the behaviour of a particular data type, but not concerned about who implements its behaviour. | - declare non-static or non-final fields (e.g. name, age) <br /><br />- allows for access and modification of the state of an object (`getName()`, `setName()`) |
|                                   | separate different behaviours (e.g. `List` interface & implementations `ArrayList  ` and `LinkedList`) | when you have a requirement for your base class to provide a default implementation of certain methods but other methods should be open to being overridden by child classes |



### Generics

- Generics allow us to create classes in the faces of methods that take types of parameters called type parameters.
- if the type parameter extends both classes and interfaces, classes need to come before interfaces
  - e.g. ` < T extends PlayerClass & Interface1 & Interface2 > ` 

```java
public static void main(String[] args) {
    // ArrayList items = new ArrayList();
    // ArrayList uses Generic type, but the type param is not specified here,
    // i.e. raw type is used -> not recommended

    ArrayList<Integer> items = new ArrayList<Integer>();
    items.add(1);
    items.add(2);
    items.add(3);
    // items.add("hello"); // gives compilation error after declaring <Integer>
    // no compilation error: raw type, unchecked type params
    // but there will be runtime error: Casting exception (String cannot be casted to Integer)
    items.add(4);
    items.add(5); // auto-boxing to convert primitive int to Integer class instances

    printDoubled(items);
}
private static void printDoubled(ArrayList<Integer> n) {
    //        for (Object i : n) {
    //            System.out.println((Integer) i * 2); // cast Object to Integer and unboxing for multiplication;
    //            // cannot multiply w/o casting
    //        }
    for (int i : n) {  // unboxing Integer
        System.out.println(i * 2);
    }
}
```

### Exceptions

- an exception is an event which occurred during the execution of a program that disrupts the normal flow of the program's instructions
- **LBYL:** Look Before You Leap
- **EAFP:** Easy to Ask for Forgiveness than Permission

```java
public class Main {

    public static void main(String[] args) {
	    int x = 98;
	    int y = 0;

        System.out.println(divideLBYL(x, y));
        System.out.println(divideEAFP(x, y));
        System.out.println(divide(x, y)); // gives ArithemeticException
    }

    private static int divideLBYL(int x, int y) {
        // check for the validness of input before execution
        if (y != 0) {
            return x / y;
        } else {
            return 0;
        }
    }

    private static int divideEAFP(int x, int y) {
        // execute first then check for the validness of input
        try {
            return x / y;
        } catch (ArithmeticException e) {
            return 0;
        }
    }
    
    private static int divide(int x, int y) {
        return x / y;
    }
}
// 0
// 0

private static int getIntLBYL() {
    Scanner s = new Scanner(System.in);
    boolean isValid = true;
    System.out.println("Please enter an integer ");
    String input = s.next();
    for (int i = 0; i < input.length(); i++) {
        if (!Character.isDigit(input.charAt(i))) {
            // check if each character in the String input is a digit
            isValid = false;
            break;
        }
    }
    if (isValid) {
        return Integer.parseInt(input);
    }
    return 0;
}

// EAFP is shorter here
private static int getIntEAFP() {
    Scanner s = new Scanner(System.in);
    System.out.println("Please enter an integer ");
    try {
        return s.nextInt();
    } catch (InputMismatchException e) {
        return 0;
    }
}
```

```java
package academy.learnprogramming;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Example {
    public static void main(String[] args) {
        try {
            int result = divide();
            System.out.println(result);
        } catch (ArithmeticException | NoSuchElementException e) { // right way to catch multiple exceptions
            System.out.println(e.toString());
            System.out.println("Unable to perform division, autopilot shutting down");
        }

    }

    private static int divide(){
        int x, y;
//        try {
            x = getInt();
            y = getInt();
            System.out.println("x is " + x + ", y is " + y);
            return x/y;
//        } catch (NoSuchElementException e) {
//            throw new ArithmeticException("no suitable input");
//        } catch (ArithmeticException e) {
//            throw new ArithmeticException("attempt to divide by zero");
//        }
        // ctrl + D terminates the scanner; hence, even tho getInt(); is called again, there is no chance to enter another int

//        System.out.println("x is " + x + ", y is " + y);
//        try {
//            return x/y;
//        } catch (ArithmeticException e) {
//            throw new ArithmeticException("attempt to divide by zero");
//        }
    }

    private static int getInt(){
        Scanner s = new Scanner(System.in);
        System.out.println("Please enter an integer: ");
        while (true) {
            try {
                return s.nextInt();
            } catch (InputMismatchException e) {
                // go round again. Read past the ens of line in the input first
                s.nextLine(); // refresh
                System.out.println("Please enter a number using only the digits 0 to 9: ");
            }
        }
    }
}

```

### Final keyword

- generally used to define constant value (tho they are not constants strictly speaking)
- `final` variables can only be modified once and any modification must be performed before the class constructor finishes (even if it's `public` but it's usually assigned `private`)
  - i.e. we can assign a final fields its value either when we first declare it or in the constructor
  - usually declared as `private static final` as the value would not change -> unnecessary to store a copy in every single class instance
- `final` classes cannot be sub-classed, i.e. cannot extend final classes (e.g. Math - private constructor)
- `final` methods cannot be overridden

## Naming Conventions

| Type                | Conventions                                                  |
| ------------------- | ------------------------------------------------------------ |
| **Packages**        | - lower case<br />- unique names<br />- use your internet domain name, reversed, as a prefix for the package name<br />- see more in Oracle |
| **Classes**         | - CamelCase<br />- Nouns<br />                               |
| **Interfaces**      | - CamelCase<br />- -able                                     |
| **Methods**         | - mixedCase<br />- often verbs to reflect the function performed or the result returned |
| **Constants**       | - ALL UPPER_CASE<br />- separate words with underscore _<br />- declared using the `final` keyword |
| **Variable names**  | - mixedCase<br />- no underscores                            |
| **Type Parameters** | - Single Character<br />- capital letter<br />- E - element (used extensively by the Java Collections Framework)<br />- K - key<br />- T - type<br />- V - value<br />- S, U, V, etc - 2nd, 3rd, 4th types |

