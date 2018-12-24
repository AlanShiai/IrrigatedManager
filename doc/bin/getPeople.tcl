#!/usr/bin/tclsh

###############################################################################
## Author      : Aijun, Shi (Aijun.Shi@windriver.com)
## Create Time : Jun.1 2016
## Modify      : Sep.25 2017 remove Xiongming, add Chenhui and Tianfeng
## In order to : Get ramdon people from dolphin_list
## Usage       : login: pek-wb-fast and run "tclsh getPeople.tcl"
################################################################################

#set dolphin_list [list ����˧ ��ٻ ��־�� ������ ʯ���� ���� ����� ��� ������ ������ ��˼�� Ϳ�� ���� ������ ������ �Ż�ӱ ��Զ ���� ������]

set dolphin_list [list Boshuai LaiQian Zhixiong Zhensheng Alan Johnny WUYujin XuGang Ryan  Meihong Siru Hong GeLei Binghui Tingting Huiying Yuan Chenhui Tianfeng LiuLei ]


puts {};

set total [llength $dolphin_list]
set rand [expr {int ($total * rand())}]

puts {::::::::::::::::::::::::::::::::::::::::::::}
puts "Dolphin Team Total Number $total"
puts {::::::::::::::::::::::::::::::::}
puts $dolphin_list
puts {::::::::::::::::::::::::::::::::::::::::::::}

puts {}; puts {}; puts {}; puts {}; puts {}; 
puts -nonewline {*** }
puts -nonewline {Lucky person is : }
puts -nonewline [lindex $dolphin_list $rand]
puts -nonewline { *** }
puts {}; puts {}; puts {}; puts {}; puts {}; 

puts {::::::::::::::::::::::::::::::::::::::::::::}

