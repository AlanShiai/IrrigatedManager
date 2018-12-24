#! /usr/bin/tclsh

set PWD [pwd]
puts "\nPWD is $PWD\n"
set chan [open "java_file.list"]

while {[gets $chan filePath] >= 0} {
#	if { [string first "test" $filePath] >=0 } {
#		continue;
#	}
	set tmpf [file join $PWD $filePath]
	set foundCopyright "false"
	if [file exists $tmpf] {
		set inf [open $tmpf]
		while {[gets $inf line] >= 0} {
			if { [string first "Copyright" $line] >=0 } {
				set foundCopyright "true"
				break;
			}
		}
		if { [string equal $foundCopyright "false"] } {
			puts $tmpf
			exec mv $tmpf tmp_java
			exec cat copy.txt tmp_java > $tmpf
			break;
		}
		close $inf
	}
}
close $chan

