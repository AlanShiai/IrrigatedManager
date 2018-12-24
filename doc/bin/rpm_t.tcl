#! /usr/bin/tclsh

set PWD [pwd]
puts "\nPWD is $PWD\n"

set chan [open "pom.list"]

set num 0

while {[gets $chan filePath] >= 0} {
	set tmpf [file join $PWD $filePath]
	if [file exists $tmpf] {
		set linenum 0
		set inf [open $tmpf]
		while {[gets $inf line] >= 0} {
			incr linenum
			if { [string first "com.windriver.maven-build" $line] >=0 } {
				incr num
				puts "${tmpf}@[expr $linenum + 1]"
				break;
			}
		}
		close $inf
	}
}
close $chan

puts $num

