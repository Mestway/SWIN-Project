//omitting namespaces for most methods and classes
//in total: 18

//constructor
{
	() 
	[ DocumentBuilderFactory.newInstance():DocumentBuliderFactory
                                          -> new SAXReader():SAXReader ]
}

{
	(x: DocumentBuilderFactory ->> SAXReader) 
	[ x.newDocumentBuilder():DocumentBuilder -> x:SAXReader ]
}


//setProperties and getProperties
{
	(x: DocumentBuilderFactory ->> SAXReader, y: boolean ->> boolean) 
    [ x.setValidating(y):void -> x.setValidation(y):void ] 
}

{
	(x: DocumentBuilderFactory ->> SAXReader, y: boolean ->> boolean)
    [ x.setIgnoringElementContentWhitespace(y):void
    -> x.setStripWhitespaceText(y):void ]
}

{
	(x: DocumentBuilderFactory ->> SAXReader, y: boolean ->> boolean) 
    [ x.setIgnoringComments(y):void
                               -> x.setIgnoreComments(y):void ]
}

{
	(x: DocumentBuilderFactory ->> SAXReader) 
	[ x.isValidating():boolean
    -> x.isValidating():boolean ]
}

{
	(x: DocumentBuilderFactory ->> SAXReader) 
	[ x.isIgnoringElementContentWhitespace():boolean
    -> x.isStripWhitespaceText():boolean ]
}

{
	(x: DocumentBuilderFactory ->> SAXReader) 
	[ x.isIgoringComments():boolean
    -> x.isIgoreComments():boolean ]
}

//read or parse
{
	(x: DocumentBuilder ->> SAXReader, y: InputStream ->> InputStream)
    [ x.parse(y):org.w3c.dom.Document
                 -> x.read(y):org.dom4j.Document ]
}

{
	(x: DocumentBuilder ->> SAXReader, y: InputStream ->> InputStream, z: String ->> String)
    [ x.parse(y, z):Document
                    -> x.read(y, z):Document ]
}

{
	(x: DocumentBuilder ->> SAXReader, y: String ->> String)
    [ x.parse(y):Document
                 -> x.read(new URL(y)):Document ]
}

{
	(x: DocumentBuilder ->> SAXReader, y: File ->> File)
    [ x.parse(y):Document
                 -> x.read(y):Document ]
}

{
	(x: DocumentBuilder ->> SAXReader, y: InputSource ->> InputSource)
    [ x.parse(y):Document
                 -> x.read(y):Document ]
}

//process Document
{
	(x: Document ->> Document) 
	[ x.getDocumentElement():Element
                -> x.getRootElement():Element ] 
}

{
	(x: Element ->> Element) 
	[ x.getChildNodes():NodeList
     -> x.elements():List ]
}


//process NodeList
{
	(x: NodeList ->> List) 
	[ x.getLength():int
		   -> x.size():int ]
}

{
	(x: NodeList ->> List, y: int ->> int) 
	[ x.item(i):Object
        -> x.get(i):Object ]
}

{
	(x: NodeList ->> List, y: int ->> int) 
	[ x.item(i):Element
         -> x.get(i):Element ]
}
