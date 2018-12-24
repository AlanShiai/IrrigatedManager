#! /bin/tclsh

###############################################################################
# Author      : Aijun, Shi (Aijun.Shi@windriver.com)
# Create Time : Sep.18 2017
# In order to : Check miss license.htm include in build.properties.
# Usage       : tclsh modify_build.properties.tcl feature.list
# Run dir     : /d/mywork/workbench3/git3/workbench/components
###############################################################################

set PWD [pwd]
puts "\nPWD is $PWD\n"

set chan [open "feature.list"]
set dirs "";

puts {check "build.properties" "license.htm" "feature.xml" "feature.properties" exists.}
set checkFiles [list "build.properties" "license.htm" "feature.xml" "feature.properties"]    
while {[gets $chan dir] >= 0} {
    foreach checkFile $checkFiles {
        set tmpf [file join $PWD $dir $checkFile]
        if { ! [file exists $tmpf] } {
            puts "file not exists: $tmpf";
            break;
        }
    }
    lappend dirs $dir
}
close $chan;
puts {check "build.properties" "license.htm" "feature.xml" "feature.properties" exists. done}
puts ""

foreach dir $dirs {
    set tmpf [file join $PWD $dir "build.properties"]
    set inf [open $tmpf]
    set flag "false"
    while {[gets $inf line] >= 0} {
        if {[string first "license.htm" $line] >= 0} {
            set flag "true"
        }
    }
    if [string equal $flag "false"] {
        puts [file join $PWD $dir]
    }
}