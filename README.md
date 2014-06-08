This is an implementation of SWIN adaptation framework  based on polyglot-1.3.5 with JL5 extension. 

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

        1. SWINCode.sw
        2. input source code

1. SWINCode.sw (This is the SWIN program you wish to transform Java code)

    The syntax of SWIN is defined as:
	
	{(x:A->>B) [x.f():C -> x.g():D]}

    e.g.  {
			(x:Vector->>ArrayList)
			[x.elements():Enumeration -> x.iterator():Iterator]
		  }

2. Input source code

       The input source file should have a suffix of .update, e.g.
        
        TestCase.update


Run
============================================================================

To run a test case, use ./test in the base directory. e.g.

        ./test yourmatchinfo APIList.api input1.update input2.update input3.update .......

1.**input1.update** specifies the file path you wish to update.
2.**APIList** specifies which files in the input are APIs (which need not to be transformed). 
3.And **yourmatchinfo** specifies the path for your SWIN program.

Output
============================================================================
The output will be displayed in the **output** directory.



