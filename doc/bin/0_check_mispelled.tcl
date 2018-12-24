#! /usr/bin/tclsh

###############################################################################
# Author      : Aijun, Shi (Aijun.Shi@windriver.com)
# Create Time : Jun.1 2016
# In order to : Check mispelled words for *properties resource file.
# Usage       : copy it and "0ignore.list" to folder "workbench/components"
#             : and run it. and check 0_result.txt file.
###############################################################################

exec find -name *properties > "alist.properties"
exit

set PWD [pwd]

set result_f [open 0_result.txt w]
puts $result_f "==================================================================================="
puts $result_f "file : xxxx"
puts $result_f "mispelled words list"
puts $result_f "==================================================================================="

set chan [open "alist.properties"]
set lineNumber 0
while {[gets $chan file_name] >= 0} {
    if [regexp {/sv/|/sda/} $file_name] {
	puts " check mispelled words for << $file_name >>>"

	set inf [open [file join $PWD $file_name]]
	set tmpf [file join $PWD tmp $file_name]
        if { ! [file exists [file dirname $tmpf ]] } {
		file mkdir [file dirname $tmpf]
	}
	set outf [open $tmpf w]
	while {[gets $inf line] >= 0} {
	    regsub {^.*=} $line " " line
	    regsub  {0x} $line " " line
	    regsub  {&} $line "" line
	    regsub -all {\\n} $line " " line
	    regsub  {\\} $line " " line
	    regsub  {/} $line " " line
	    regsub {:} $line " " line
	    regsub {\.\.\.} $line " " line
	    regsub {\{\d\}} $line " " line
	    regsub {n't} $line " " line
	    puts $outf $line
	}
	close $outf
	close $inf

	puts $result_f "file : $file_name"
	exec hunspell -p 0ignore.list -l $tmpf >@ $result_f 


    }
}

close $chan
close $result_f

puts ""
puts ""
puts "==================================================================================="
puts "done."
puts "please check result file : 0_result.txt"
puts "==================================================================================="
puts ""
puts ""
