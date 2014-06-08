//omitting namespaces
//in total : 80

//Migrating event resources
{
	() 
	[ (new CalendarEventEntry()):CalendarEventEntry 
            -> (new Event()):Event ]
}

{
	(event: CalendarEventEntry ->> Event) 
	[ event.getAuthors():List<Person>
              -> event.getCreator():Event.Creator ]
}

{
	(x: List<Person> ->> event.Creator) 
	[ x.get(0):Person
                 -> x:event.Creator ]
}


{
	(x: Person ->> event.Creator) [ x.getName():String
                -> x.getDisplayName():String ]
}

{
	(x: Person ->> event.Creator) 
	[ x.getEmail():String
           -> x.getEmail():String ]
}

{
	(event: CalendarEventEntry ->> Event) 
	[ event.getId():String
                 -> event.getId():String ]
}

#TODO: What is x.substring(_)?
{
	(x: String ->> NoString) 
	[ x.substring(_):String
           -> (new NoString()):NoString ]
}

{
	(event: CalendarEventEntry ->> Event) 
	[ event.getPublished():DateTime
        -> event.getPublished():EventDateTime ]
}

{
	(event: CalendarEventEntry ->> Event) 
	[ event.getUpdated():DateTime
             -> event.getUpdated():EventDateTime ]
}

{
	(event: CalendarEventEntry ->> Event) 
	[ event.getKind():String
            -> event.getKind():String ]
}

{
	(event: CalendarEventEntry ->> Event) 
	[ event.getPlainTextContent():String
               -> event.getDescription():String]
}

{
	(event: CalendarEventEntry ->> Event) 
	[ event.getHtmlLink():Link
                -> event:Event ]
}


{
	(x: Link ->> Event) 
	[ x.getHref():String
          -> x.getHtmlLink():String]
}

{
	(event: CalendarEventEntry ->> Event) 
	[ event.getTitle():TextConstruct
               -> event:Event ]
}


{
	(x: TextConstruct ->> Event) 
	[ x.getPlainText():String
            -> x.getSummary():String ]
}

{
	(event: CalendarEventEntry ->> Event) 
	[ event.getStatus():BaseEventEntry.EventStatus
                     -> event:Event ]
}

{
	(x: BaseEventEntry.EventStatus ->> Event) 
	[ x.getValue():String -> x.getStatus() ]
}

{
	(event: CalendarEventEntry ->> Event) 
	[event.getOriginalEvent():OriginalEvent
             -> event:Event ]
}

{
	(x: OriginalEvent ->> Event) 
	[ x.getOriginalId():String
     -> x.getRecurringEventId():String ]
}


{
	(x: OriginalEvent ->> Event) 
	[ x.getOriginalStartTime():When
                   -> x:Event ]
}

{
	(x: When ->> Event) 
	[ x.getStartTime():String
       -> x.getOriginalStartTime():String ]
}

{
	(event: CalendarEventEntry ->> Event) 
	[ event.getRecurrence():Recurrence -> event:Event ]
}

{
	(x: Recurrence ->> Event) 
	[ x.getValue():String -> x.getRecurrence():List ]
}

{
	(event: CalendarEventEntry ->> Event) 
	[ event.getTransparency():CalendarEventEntry.Transparency -> event:Event ]
}


{
	(x: CalendarEventEntry.Transparency ->> Event) 
	[ x.getValue():String -> x.getTransparency():String ]
}

{
	(event: CalendarEventEntry ->> Event) 
	[ event.getVisibility():CalendarEventEntry.Visibility -> event:Event ]
}

{
	(x: CalendarEventEntry.Visibility ->> Event) 
	[ x.getValue():String -> x.getVisibility():String ]
}

{
	(event: CalendarEventEntry ->> Event) 
	[ event.getTimes():List<When> -> event:Event ]
}

{
	(x:List<When> ->> Event) 
	[ x.get(0):When -> x:Event ]
}

{
	(x:When ->> Event) 
	[ x.getStartTime():DateTime
            -> x.getStart():EventDateTime ]
}

{
	(x:When ->> Event) 
	[ x.getEndTime():DateTime
            -> x.getEnd():EventDateTime ]
}

{
	() 
	[ (new Reminder()):Reminder
         -> (new EventReminder()):EventReminder ]
}

{
	(event: CalendarEventEntry ->> Event) 
	[ event.getReminders():Reminder
        -> event.getReminders():EventReminder ]
}

{
	(x: Reminder ->> EventReminder) 
	[ x.getMinutes():int -> x.getMinutes():int ]
}

{
	(event: CalendarEventEntry ->> Event) 
	[ event.getLocations():List<Where> -> event:Event ]
}

{
	(x: List<Where> ->> Event) 
	[ x.get(0):Where -> x:Event ]
}

{
	(x: Where ->> Event) 
	[ x.getValueString():String -> x.getLocation():String ]
}

{
	() 
	[ (new EventWho()):EventWho
                      -> (new EventAttendee()):EventAttendee ]
}

{
	(x: EventWho ->> EventAttendee) 
	[ x.getParticipants():EventWho
        -> x.getAttendees():EventAttendee ]
}

{
	(x: EventWho ->> EventAttendee) 
	[ x.getValueString():String
		-> x.getDisplayName():String ]
}

{
	(x: EventWho ->> EventAttendee) 
	[ x.getEmail():String
            -> x.getEmail():String ]
}

{
	(x: EventWho ->> EventAttendee) 
	[ x.getAttendeeStatus():String
         -> x.getResponseStatus():String ]
}

{
	(event: CalendarEventEntry ->> Event) 
	[ x.getSequence():int -> x.getSequence():int ]
}

{
	(event: CalendarEventEntry ->> Event) 
	[ x.getIcalUID():String -> x.getCalUID():String ]
}

{
	() 
	[ (new WebContent()):WebContent
    -> (new EventGadget()):EventGadget ]
}

{
	(event: CalendarEventEntry ->> Event) 
	[ event.getWebContent():WebContent
     -> event.getGadget():EventGadget ]
}

{
	(x: WebContent ->> EventGadget) 
	[ x.getIcon():String
         -> x.getIcon():String ]
}

{
	(x: WebContent ->> EventGadget) 
	[ x.getTitle():String
         -> x.getTitle():String ]
}

{
	(x: WebContent ->> EventGadget) 
	[ x.getType():String
            -> x.getType():String ]
}

{
	(x: WebContent ->> EventGadget) 
	[ x.getUrl():String
     -> x.getUrl():String ]
}

{
	(x: WebContent ->> EventGadget) 
	[ x.getWidth():String
         -> x.getWidth():String ]
}

{
	(x: WebContent ->> EventGadget) 
	[ x.getHeight():String -> x.getHeight():String ]
}

{
	(x: WebContent ->> EventGadget) 
	[ x.getGadgetPrefs():Map.Entry<String, String>
             -> x.getPreferences():Map.Entry<String, String> ]
}

{
	() 
	[ x.getKey():String
                -> x.getKey():String ]
}

{
	() 
	[ x.getValue():String -> x.getValue():String ]
}

{
	(event: CalendarEventEntry ->> Event) 
	[ event.isGuestsCanModify():boolean
         -> event.getGuestsCanModify():boolean ]
}

{
	(event: CalendarEventEntry ->> Event) 
	[ event.isGuestsCanInviteOthers():boolean
         -> event.getGuestsCanInviteOthers:boolean ]
}

{
	(event: CalendarEventEntry ->> Event) 
	[ event.isGuestsCanSeeGuests():boolean
         -> event.getGuestsCanSeeOtherGuests():boolean ]
}

//Migrating calendar resources
{
	() 
	[ (new CalendarEntry()):CalendarEntry
         -> (new Calendar()):Calendar ]
}

{
	(c: CalendarEntry ->> Calendar) 
	[ c.getEtag():String
       -> c.getEtag():String ]
}

{
	(c: CalendarEntry ->> Calendar) 
	[ c.getKind():String
         -> c.getKind():String ]
}

{
	(c: CalendarEntry ->> Calendar) 
	[ c.getId():String
         -> c.getId():String ]
}

{
	(c: CalendarEntry ->> Calendar) 
	[ c.getTitle():TextConstruct -> c:Calendar ]
}

{
	(x: TextConstruct ->> Calendar) 
	[ x.getPlainText():String
         -> x.getSummary():String ]
}

{
	(c: CalendarEntry ->> Calendar) 
	[ c.getSummary():TextConstruct -> c:Calendar ]
}

{
	(x: TextConstruct ->> Calendar) 
	[ x.getPlainText():String -> x.getDescription():String ]
}

{
	(c: CalendarEntry ->> Calendar) 
	[ c.getLocations():List<Where> -> c:Calendar ]
}

{
	(x: List<Where> ->> Calendar) 
	[ x.get(0):Where -> x:Calendar ]
}

{
	(x: Where ->> Calendar) 
	[ x.getValueString():String
         -> x.getLocation():String ]
}

{
	(c: CalendarEntry ->> Calendar) 
	[ c.getTimeZone():TimeZoneProperty -> c:Calendar ]
}

{
	(x: TimeZoneProperty ->> Calendar) 
	[ x.getValue():String -> x.getTimeZone():String ]
}


//Migrating ACL resources
{
	() 
	[ (new AclEntry()):AclEntry -> (new AclRule()):AclRule ]
}

{
	(a: AclEntry ->> AclRule) 
	[ a.getEtag():String -> a.getEtag():String ]
}

{
	(a: AclEntry ->> AclRule) 
	[ a.getKind():String -> a.getKind():String ]
}

{
	(a: AclEntry ->> AclRule) 
	[ a.getId():String -> a.getId():String ]
}

{
	(a: AclEntry ->> AclRule) 
	[ a.getRole():AclRole -> a:AclRule ]
}

{
	(x: AclRole ->> AclRule) 
	[ a.getValue():String -> a.getRole():String ]
}

{
	(a: AclEntry ->> AclRule) 
	[ a.getScope():AclScope -> a:AclRule.Scope ]
}

{
	(x: AclScope ->> AclRule.Scope) 
	[ x.getType():AclScope.Type -> x.getType():String ]
}

{
	(x: AclScope ->> AclRule.Scope) 
	[ x.getValue():String -> x.getValue():String ]
}
