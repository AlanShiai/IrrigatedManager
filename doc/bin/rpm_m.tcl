#! /usr/bin/tclsh

set chan [open "pom_need.list"]

while {[gets $chan line] >= 0} {
	set index [string first @ $line]
	set file [string range $line 0 [expr $index - 1] ]
	set linenum [string range $line [expr $index + 1] end ]
	puts $file
	exec sed -i "${linenum}s/4.17/4.16/" $file
	break;
}
