This is an implementation of Java Update Calculus based on polyglot-1.3.5 with JL5 extension. 

Build
=====================================================================
To build the project, make sure you have java and ant.

        ~:rm bin/jlc bin/pth
        ~:ant
        ~:ant jl
        ~:ant update

Input
============================================================================
To run a example, you need two inputs:

        1. MatchInfo.in
        2. input source code

1. MatchInfo.in (This is the match info you wished to update your client code)

    The matching grammar consists of three parts:
	
        [T1 (D1) {B1} T2 (D2) {B2}]

    e.g.  [Enumeration (Vector v) {v.elements()} Iterator (ArrayList a) {a.iterator()}]

2. Input source code

       This can be put anywhere, but the input source file should have the suffix .update, e.g.
        
        TestCase.update


Run
============================================================================

To run the test case, you need to use ./test in the base directory. The grammar is like the following:

        ./test yourmatchinfo input1.update input2.update input3.update .......

Note that **input1.update** specifies the file path you wish to update.
And that **yourmatchinfo** specifies the path for you update calculus file.

Output
============================================================================
The output will be displayed in the **output** directory.



