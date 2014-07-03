{
	()
	[new Hashtable():Hashtable -> new HashMap():HashMap]
} 

{
	(x:Hashtable ->> HashMap) 
	[x.elements():Enumeration -> x.values().iterator():Iterator]
}

{
	(x: Enumeration ->> Iterator)
	[x.hasMoreElements():boolean -> x.hasNext():boolean]
}

{
	(x:Enumeration ->> Iterator)
	[x.nextElement():Object -> x.next():Object]
}

{
	()
	[new Vector():Vector -> new ArrayList():ArrayList]
}

{
	(x: Vector ->> ArrayList)
	[x.elements():Enumeration -> x.iterator():Iterator]
}

