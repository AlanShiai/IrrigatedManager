#!/bin/sh
#
# This script is for the weekly releasing process of the Wind River Workbench 
#
# Tasks:
# -> labeling the sources
# -> building kit
# -> generate test cd
# -> checkin wrwb drops
#
#
# hints for updating to next WB version:
#	--> check cspec update_build_cspec() function
#	--> vob path for checking in the drop
# 	--> edit labeltype setlabel() fuction
#	--> adapt release cspec REL_CSPEC for building the WB
#	--> 
#
# OPTIONS: [label,build,make,checkin,startWB]  
#
# AUTHOR: It's me 






############ RELEASE DEPENDEND VARS ##############################
#Product
PRODUCT=workbench-3.3
WBVERSION=3362
WBVERSION_DOT=3.3.6.2
LABEL="FREEZE_WIND_RIVER_WORKBENCH_SOURCE_3_3_6_2_"  # version will be added _3_1_0_xx

# Release Area
RELEASE_AREA="/folk/prj-workbench/workbench"
WRWB_CONTAINER="$RELEASE_AREA/wrwb"
RELEASE_AREA_PE="/vobs/dist_2010_wb/workbench-pe"
WRWBPE_CONTAINER="$RELEASE_AREA_PE/1.0"
PRODUCT_DIR="/vobs/wind_build/products/$PRODUCT"
PRODUCT_DIR_SETUP="/vobs/wind_build/products/setup-2.1"
PRODUCT_DIR_PE="/vobs/wind_build/products/workbench-pe-1.0"
INSTALLKEY=`find /folk/r1data/for_r1/workbench/workbench-3.3.6 | grep wb_all_install.txt | sort | tail -1`
installer_cspec=installer_2_5_0-rel # Heli knows which installer version to choose 
weekliesLocation=/folk/prj-wb-dev/wrwb_store/weeklies/

# Qa's all installation for platform links
SHARED_WB_INST=/folk/szgqa/latest_3.3.6


# cspec for labeling
LABEL_CSPEC="/cspecs/salzburg/workbench_latest-rel"

# cspec for building the WB
#REL_CSPEC="/cspecs/salzburg/"$PRODUCT"/workbench_3.3.6-rel" # !hmpf!_!-!
REL_CSPEC="/cspecs/salzburg/"$PRODUCT"/workbench_$WBVERSION_DOT-rel"
#REL_CSPEC="/folk/wsadm/release/scripts/workbench_$WBVERSION_DOT-latest-buildable"





########### HOST DEPENDEND VARS ##################################
export HOME=/folk/prj-wb-dev/wsadm

SCRIPTHOME=`dirname $0`
SCRIPTHOME=`(cd $SCRIPTHOME; echo $PWD)`
public_location=/folk/vobadmin/public_html/WBRelease/
statusXmlFile="$public_location"weekly_release_status.xml
wheelReportLocation=/folk/vobadmin/public_html/WheelReports/rome_wpide

# where to drop the kit and test cd
WORK_LOCATION="/folk/prj-wb-dev/wrwb_store/weeklies/workbench-$WBVERSION_DOT"
#KIT_LOCATION="/net/szg-rhfs/szg-rhfs03/t5-dev/weeklies/workbench-$WBVERSION_DOT" 
CD_LOCATION="/folk/prj-wb-dev/wrwb_store/weeklies/workbench-$WBVERSION_DOT" 

export_prefix="/net/szg-bld-wb"
view_name="wsadm_reltmp`date +%s`" 
if [ "`hostname`" = "szg-cc-pb01l" ]; then
	view_path="/szg-cc-pb01l01/t5-dev/view_store/$view_name" # where the view will be created 
elif [ "`hostname`" = "szg-bld-wb.wrs.com" ]; then
	view_path="/export1/view_store/$view_name"
elif [ "`hostname`" = "ala-rh6-2" ]; then
	export_prefix=
	view_path="/folk/prj-wb-dev/view_store/$view_name"
elif [ "`hostname`" = "ala-ccbuild-lx1" ]; then
	export_prefix=
	view_path="/ala-ccbuild-lx11/view_store/$view_name.vws"
elif [ "`hostname`" = "schnibble" ]; then
	view_path="/export1/tmp/$view_name"
fi






########## MISC      #########################################

setlabel(){
	test -z $wb_version && getWBVersion
	labeltype="$LABEL""$wb_version" 
}


#RELEASE_AREA=/view/"$view_name""$RELEASE_AREA"
PRODUCT_DIR=/view/"$view_name""$PRODUCT_DIR"
PRODUCT_DIR_SETUP=/view/"$view_name""$PRODUCT_DIR_SETUP"
PRODUCT_DIR_PE=/view/"$view_name""$PRODUCT_DIR_PE"
LABEL_CSPEC=/view/$view_name"$LABEL_CSPEC"
REL_CSPEC=/view/"$view_name""$REL_CSPEC"
#WRWB_CONTAINER=/view/"$view_name""$WRWB_CONTAINER"


master_location=/ala-ccbuild-lx11/tmp/weeklyMaster_$WBVERSION
master_location_setup=/ala-ccbuild-lx11/tmp/weeklyMasterSetup_$WBVERSION
dvds_location=/ala-ccbuild-lx11/tmp/weeklyDVDs
rm -rf $dvds_location
mkdir -p $dvds_location
rm -rf $master_location
mkdir -p $master_location
rm -rf $master_location_setup
mkdir -p $master_location_setup



############ PROGRAM ######################################

# sane script end
trap cleanup 2 3 15 

printn(){

	string="$*"
	echo -n "$string"
	count=${#string}
	rem=`expr 90 - $count`
	while [ $rem -gt 0 ]; do
        	rem=`expr $rem - 1`
	        echo -n " "
	done

}

getWBVersion(){

	test -f $SCRIPTHOME/wbver$WBVERSION || exit_status f $SCRIPTHOME/wbver$WBVERSION

	# if its older than three days -> increase number
	found=`find $SCRIPTHOME -name wbver$WBVERSION -mtime +3` 
	if [ "$found" = "$SCRIPTHOME/wbver$WBVERSION" ]; then	
		wb_version=`cat $SCRIPTHOME/wbver$WBVERSION`	
		echo `expr $wb_version + 1` > $SCRIPTHOME/wbver$WBVERSION		
	fi	
	wb_version=`cat $SCRIPTHOME/wbver$WBVERSION`	
	echo "INFO: Workbench number is: $wb_version "
	if [ "$wb_version" = "" ]; then
		exit_status canc "wb version not found"
	fi
	wb_version_length=${#wb_version}	
	if [ $wb_version_length -lt 2 ]; then	
		kitversion=kit0$wb_version
	else
		kitversion=kit$wb_version
	fi
	kitversion_pe=pe_$kitversion

}

getWBPEVersion(){

	test -f $SCRIPTHOME/wbpever336 || exit_status f $SCRIPTHOME/wbpever336

	# if its older than three days -> increase number
	found=`find $SCRIPTHOME -name wbpever336 -mtime +3` 
	if [ "$found" = "$SCRIPTHOME/wbpever336" ]; then	
		wbpe_version=`cat $SCRIPTHOME/wbpever336`	
		echo `expr $wbpe_version + 1` > $SCRIPTHOME/wbpever336		
	fi	
	wbpe_version=`cat $SCRIPTHOME/wbpever336`	
	echo "INFO: WorkbenchPE number is: $wbpe_version "
	if [ "$wbpe_version" = "" ]; then
		exit_status canc "wb version not found"
	fi
	wbpe_version_length=${#wbpe_version}	
	if [ $wbpe_version_length -lt 2 ]; then	
		peversion=00$wbpe_version
	else
		peversion=0$wbpe_version
	fi

}


setview(){
	if [ ! "$view_path" ]; then
		echo "Enter Path where the view will be created"
		read an
		view_path="$an"/"$view_name"
	fi
	printn "INFO: Create view: $view_name in $view_path " 
	create_view=`cleartool mkview -tag $view_name -host ala-ccbuild-lx1 -hpath $view_path -gpath $view_path $view_path 2>&1`
	if [ "$?" = "0" ]; then
		echo " OK"
	else
		view_exists=`echo $create_view | grep "A registry entry already"`
		if [ -n "$view_exists" ]; then
			echo " OK [VIEW already exitsts]"	
		else
			echo -e "FAIL \n$create_view"
			exit 1
		fi
	fi
	sleep 1
	cd /view/$view_name  
	cleartool setcs -default
}

endview(){
	if [ "`cleartool lsview | grep $view_name`" ]; then
		printn "INFO: Remove view $view_name in $view_path"
		cd ~
		rm_view=`cleartool rmview -tag $view_name 2>&1`
		if [ "$?" = "0" ]; then
			echo " OK"
		else
			echo -e "ERROR \n$rm_view"
			exit 1
		fi
	fi	
}

# for trap
cleanup(){
	exit_status canc
	exit
}

create_lbtype(){
	# CREATE LBTYPE # 
	cd /view/$view_name/vobs/rome_admin
	cleartool describe lbtype:$labeltype > /dev/null 2>&1
	if [ "$?" = "0" ]; then
		echo "INFO: Label $labeltype exists already"
	else
		echo -n "INFO: Create labeltype: $labeltype in $PWD"
		create_label=`cleartool mklbtype -global -c "WSADM CREATE label for building WRWB $wb_version " $labeltype 2>&1` 
		if [ "$?" = "0" ]; then
			echo " OK"
		else
			echo "FAIL"
			exit_status lt $labeltype $create_lbtype
		fi
	fi
}


label(){
	# VARIABLES ###############
	setview
	getWBVersion
	setlabel
	label_command="/bin/bash label_all_wpide_sources.sh -v -u $view_name -i -l $labeltype"

	# create label type
	create_lbtype

	# Check cspec
	printn "INFO: Check cspec: $LABEL_CSPEC"
	test -f $LABEL_CSPEC || exit_status f $LABEL_CSPEC
	echo " OK"
	echo ""
	echo "----------- cspec to use for labeling sources --------------------------"
	cat $LABEL_CSPEC 
	echo "------------------------------------------------------------------------"
	echo ""


	# PROGRAM ####################
	echo "INFO: Cleartool setcs $LABEL_CSPEC"
	cd /view/$view_name
	cleartool setcs $LABEL_CSPEC 2>&1
	echo ""

	cd /view/$view_name/vobs/rome_wpide/admin
	$label_command
	if [ ! "$?" = "0" ]; then
		exit_status canc
	fi
	
	# tear down
	cd /view/$view_name/vobs/rome_admin
	cleartool lock lbtype:$labeltype
	endview
}

findchangeset(){
	setview
	getWBVersion
	changesetFile="changeset$wb_version.txt"
	let "wb_version = $wb_version - 1"
	setlabel
	findchangesetcommand="/bin/bash /vobs/rome_wpide/admin/find_changesets_on_branch.sh -v -b wb3.3.2_bugfix -r -l $labeltype"
	# PROGRAM ####################
	echo "INFO: Cleartool setcs $LABEL_CSPEC"
	cd /view/$view_name
	cleartool setcs $LABEL_CSPEC 2>&1
	cat $LABEL_CSPEC
	echo ""
	#cd /view/$view_name/vobs/rome_wpide/admin

	echo "INFO cmd: $findchangesetcommand"
	echo "FILE: ~/$changesetFile"
	ls ~/$changesetFile
	cleartool setview -exec "$findchangesetcommand" $view_name > ~/$changesetFile

#	cd /vobs/rome_wpide/admin
#	sleep 10
#	$findchangesetcommand > ~/changeset$wb_version.txt
	endview
}


# param1 is the error type 
# param2 when given must be a filename
# param3 when giver error message
exit_status(){
	case "$1" in
	f)	echo "Error: file $2 not found"
		;;
	d)	echo "Error: directory $2 not found"
		;;
	w)	echo "Error: file $2 is not writeable"
		;;
	c)	echo "Error: could not create directory $2"
		;;
	lx)	echo "Error: Labeltype $2 does not exist"
		;;
	lt) 	echo "Error: Could not create labeltype $2"
		echo "MESSAGE: $3"
		;;
	co)	echo "ERROR: Could not checkout $1"
		echo "MESSAGE: $2"	
		echo "END ERROR"
		;;
	canc)	echo "INFO: progress canceled"
		echo "$2"
		;;
	*)	echo "Error unknown"
		;;
	esac
	echo " "
	endview
	exit 1
}

# Helper function for build() 
update_build_cspec(){

	getWBVersion
	setview
	setlabel

	# finding cspec
	echo "INFO: check if cspec $REL_CSPEC exits"
	test -f $REL_CSPEC || exit_status f $REL_CSPEC

	# checkout
	printn "INFO: update cpsec $REL_CSPEC"
	checkout=`cleartool co -nc $REL_CSPEC 2>&1`
	if [ ! "$?" = "0" ]; then
		exit_status co $REL_CSPEC $checkout
	fi


	cat $REL_CSPEC | grep $labeltype > /dev/null 2>&1
	if [ ! "$?" = "0" ]; then

		# update
		test -w $REL_CSPEC || exit_status w $REL_CSPEC
		echo "# Selects: WIND RIVER WORKBENCH, version 3.3 " > $REL_CSPEC
		echo "element * .../wb3.3.2_bugfix/$labeltype -nocheckout" >> $REL_CSPEC
		echo "element * /main/$labeltype -nocheckout" >> $REL_CSPEC
		echo " OK"

		# checkin
		printn "INFO: checkin $REL_CSPEC"
		checkin=`cleartool ci -c "WSADM INCREASE label due to releasing the Workbench $wb_version.10FA " $REL_CSPEC`
		if [ ! "$?" = "0" ]; then
			exit_status ci $REL_CSPEC $checkin
		fi
		echo " OK"
	else
		echo "INFO: cspec $REL_CSPEC is already up2date"
		cat $REL_CSPEC
		echo ""
	fi

}

build(){
	# VARIABLES ######################################
	build_script_home="/view/$view_name/vobs/rome_wpide/admin"
	build_script="createWPIDE2_pde.sh"
	
	# PREPARE #######################################
	getWBVersion
	setview
	setlabel

	# create build dir
	if [ -d $WORK_LOCATION/$kitversion ]; then
		echo -n "WARNING: directory: $WORK_LOCATION/$kitversion does exist."
	else
		printn "INFO: create kit directory: $kitversion in $WORK_LOCATION"
		mkdir -p $WORK_LOCATION/$kitversion || exit_status c $WORK_LOCATION/$kitversion
		echo " OK"
	fi

	echo ""
	echo _________________________cspec for building the WB _______________________
	cat $REL_CSPEC
	echo __________________________________________________________________________
	echo ""

	# find build script
	test -d $build_script_home || exit_status d $build_script_home 
	#cd $build_script_home
	test -f $build_script_home/$build_script || exit_status f $build_script
	echo " OK"

	# PROGRAM #################################
	cleartool setview -exec "/bin/bash /vobs/rome_wpide/admin/$build_script -v -c $REL_CSPEC -t $WORK_LOCATION/$kitversion -r -s" $view_name
	if [ "$?" = "0" ]; then
		echo "INFO: Build finished"
	else
		echo "ERROR: Build failed"
		cat $build_script_home/ant_output_*.txt
		echo "BUILD VIEW Not removed"
		exit 1
		exit_status canc
	fi
	cd $WORK_LOCATION/$kitversion
	mkdir sdk
	mv wrwb-sources.tar.gz sdk/
	mv wrwb-eclipse.unused.tar.gz sdk/
	mv wrwb-eclipse-sources.tar.gz sdk/
	mv wrwb-target-extra.tar.gz sdk/
	mv wrwb-tests.tar.gz sdk/
	mv wrwb-ocdagent.tar.gz sdk/
	mkdir obsolete
	mv wrwb-docs.tar.gz obsolete/
	mkdir inp
	cd inp
	cp /net/szg-rhfs/szg-rhfs03/t5-dev/inp/*inp* .

#	mkdir -p $WORK_LOCATION/$kitversion

	# PROGRAM #################################
#	cleartool setview -exec "/bin/bash /vobs/rome_wpide/admin/$build_script -v -c $REL_CSPEC -t $WORK_LOCATION/$kitversion_pe -r -s -o" $view_name
#	cleartool setview -exec "/bin/bash /vobs/rome_wpide/admin/$build_script -v -c $REL_CSPEC -t $WORK_LOCATION/$kitversion_pe -r -o" $view_name
#	if [ "$?" = "0" ]; then
#		echo "INFO: Build finished"
#	else
#		echo "ERROR: Build failed"
#		cat $build_script_home/ant_output_*.txt
#		exit_status canc
#	fi

#	mkdir /tmp/phoenix_release_packages_weekly
#	cd /tmp/phoenix_release_packages_weekly
#	git clone ssh://git.wrs.com/git/projects/phoenix/phoenix-release 
#	cd phoenix-release
#	make
#	cp archives/phoenix* $WORK_LOCATION/$kitversion
#	rm -rf /tmp/phoenix_release_packages_weekly

#	cd $WORK_LOCATION/$kitversion
#	mkdir wrpe
#	cp $WORK_LOCATION/$kitversion/wrwb-sources.tar.gz .
#	cp $WORK_LOCATION/$kitversion/wrwb-wrpecommon.tar.gz wrpe/
#	cp $WORK_LOCATION/$kitversion/wrwb-phoenix-tools.tar.gz wrpe/
#	cp $WORK_LOCATION/$kitversion/wrwb-wrpe.tar.gz wrpe/
#	cp $WORK_LOCATION/$kitversion/phoenix* wrpe/
	endview
}

echoCDLocation(){
	#getWBVersion
	echo $CD_LOCATION/wb$kitversion
}

makeCD(){
	# PREPARE #######
	getWBVersion
	setview
	
	#HACK IF THE DFW.mk file was updated and the new drop is not available yet
	rm /tmp/weeklyCDbuildcspec.txt
	touch /tmp/weeklycspec.txt
	echo "element * CHECKEDOUT" > /tmp/weeklyCDbuildcspec.txt
#	echo "element /vobs/wind_build/products/workbench-3.3/mfg_332/generatorConfig.xml /main/67" >>/tmp/weeklyCDbuildcspec.txt
	echo "element * /main/LATEST" >> /tmp/weeklyCDbuildcspec.txt
	cleartool setcs /tmp/weeklyCDbuildcspec.txt
	rm -f /tmp/weeklyCDbuildcspec.txt
	# END HACK


	echo "INFO: make CD"
	echo ""

	make_command="make WRWB_ROOT=$WORK_LOCATION/$kitversion IMAGEDIR=$master_location DFW_ROOT=/folk/prj-workbench/workbench/dfw/full_install/3_3_6_7  PLINK_ROOT=/folk/prj-workbench/toolchains/plink/3_3_6_7 OCD_ROOT=/folk/prj-workbench/workbench/ocd/3_3_6_QA9 HOSTSHELL_ROOT=/folk/prj-workbench/workbench/hostShell/3.3.6  HOSTSHELL_TEST_ROOT=/folk/prj-workbench/workbench/hostShell/test/3.3.6  VIEW=$view_name MFG_SETTINGS=mfg_3362"
	create_installprop_command="make WRWB_ROOT=$WORK_LOCATION/$kitversion IMAGEDIR=$master_location VIEW=$view_name install.properties MFG_SETTINGS=mfg_3362"

	cp $PRODUCT_DIR/mfg_3362/* $PRODUCT_DIR

	cd $PRODUCT_DIR 
	if [ ! "$?" = "0" ]; then
		exit_status d $PRODUCT_DIR 
	fi
	test -f Makefile || exit_status f $PRODUCT_DIR/Makefile
	echo "INFO: Make master -> command: $make_command"
	
	eval $make_command
	if [ ! "$?" = "0" ]; then
		echo "ERROR: Something went wrong with make!"
		exit_status canc
	fi
	eval $create_installprop_command

	# manufacture DVDS
	echo ""
	echo "INFO: Manufactur DVD..."
	echo ""

	/view/$view_name/vobs/cm_tools/wr/bin/runInstallerApplication.sh -cspec /view/$view_name/cspecs/salzburg/installer/2.5/$installer_cspec -application generator -configFile /view/$view_name/vobs/wind_build/products/$PRODUCT/mfg_3362/generatorConfig.xml -Dmfg.view=$view_name -Dmdf.path=$master_location/.resources/workbench_mdf_list.txt -DmanufactureSteps=all -Dcdr.dir=$dvds_location/images -Dimage=dvd -Ddefault=default


	# install generated DVDS
	echo ""
	echo "INFO: Install DVD to $CD_LOCATION"
	echo ""
	cd $dvds_location/dvd
	dvd_location=`ls . | grep DVD | sort -r | tail -1`
	cd $dvd_location
	./setup_linux32 -installPath $CD_LOCATION/wb$kitversion -silent -consoleLog -installKeys $INSTALLKEY -hosts all -archs all -productUpdateURLs none
	echo "INFO: create platform links to qa's $SHARED_WB_INST Installation"
	cd $CD_LOCATION/wb$kitversion
	rm -rf license
	rm -rf components
	rm -rf diab
	for i in `ls $SHARED_WB_INST | grep vxworks-` components wrhv-1.3 wrlinux-4 diab gnu license
	do
		ln -s $SHARED_WB_INST/$i
	done

	echo ""
	echo "--------------------------------------------------------------------"
	echo "FINISHED: $CD_LOCATION/wb$kitversion/	"
	echo "--------------------------------------------------------------------"
	
	echo ""
	endview
}

startWB(){
	getWBVersion
	echo "******************************************************************"
	echo "start WB"
	echo "******************************************************************"
	cd "$export_prefix"$CD_LOCATION/wb$kitversion/
	rm -rf /tmp/$kitversion.*
	"$export_prefix"$CD_LOCATION/wb$kitversion/startWorkbench.sh -data /tmp/$kitversion.ws -configuration /tmp/$kitversion.conf
	sleep 4
	echo "******************************************************************"
	echo "Check Configuration Area, the config Area should not be more than 3M"
	echo "witch debugging the cobble example the config Area is about 2.7M"
	echo "******************************************************************"
	conf_area=`du -sh /tmp/$kitversion.conf`
	echo $conf_area
	while true; do 
		check=`find /tmp/$kitversion.conf/ -type d | grep com | grep -v org.eclipse.osgi`
		if [ "$check" ]; then
			echo "******************************************************************"
			echo "CHECK THIS OUT: $check"
			echo "******************************************************************"
			check=" "
		fi
		new_conf_area=`du -sh /tmp/$kitversion.conf`
		if [ ! "$conf_area" = "$new_conf_area" ]; then
			echo $new_conf_area
			conf_area=$new_conf_area
		fi
		sleep 8
	done
}

checkin(){

	echo "INFO: Checkin, time: `date +%c`"
	getWBVersion
	setview
	setlabel
	cleartool setcs -default
	
	#copy weekly kit to rhfs03
	#cp -r $WORK_LOCATION/$kitversion $weekliesLocation/workbench-$WBVERSION_DOT
	#cleanup old kits 
	#cd $weekliesLocation/workbench-$WBVERSION_DOT

	#cd $WORK_LOCATION/$kitversion
	#mkdir sdk
	#mv wrwb-sources.tar.gz sdk/
	#mv wrwb-eclipse.unused.tar.gz sdk/
	#mv wrwb-eclipse-sources.tar.gz sdk/
	#mv wrwb-target-extra.tar.gz sdk/
	#mv wrwb-tests.tar.gz sdk/
	#mv wrwb-ocdagent.tar.gz sdk/
	#mkdir obsolete
	#mv wrwb-docs.tar.gz obsolete/
	#mkdir inp
	#cd inp
	#cp /net/szg-rhfs/szg-rhfs03/t5-dev/inp/*inp* .

	cd $WORK_LOCATION
  	OBSOLETE_DAYS=`ls -d kit* | sort -ur | sed -e '1,6d'`
  	for prefix in $OBSOLETE_DAYS ; do
    		OBSOLETE=`ls -d $prefix*`
    		for candidate in $OBSOLETE ; do
      			if [ ! -f $candidate/precious ]; then
      				rm -rf $candidate
      			fi
    		done
  	done

	# get buildID
	echo "INFO: Get build ID: "
#	cd  $CD_LOCATION/wb$kitversion
#	cd workbench-*
	cd `find wb$kitversion -name "com.windriver.ide_*" | grep plugins | grep -v src`
	test -f about.mappings || exit_status f about.mappings
	buildID=`cat about.mappings | grep 0= | awk 'BEGIN{ FS="="};{print $2}'`
	echo "$buildID 		OK"

	echo "HHA: This was a plain exit ;) exit"
	echo -n "Exit now [Y/n]?"
	read -n 1 exitnow
	echo ""
	if [ ! "$exitnow" = "n" ]; then
		exit
	fi

	# Program ###########################
	#comment="WSADM ADD new WB for WB$kitversion.10FA"
	cd $WRWB_CONTAINER
	echo "INFO: Creating $WRWB_CONTAINER"
	#verify=`cleartool co -nc . 2>&1`
	#if [ ! "$?" = "0" ]; then
	#	exit_status co $WRWB_CONTAINER $verfiy
	#fi
	umask 002
	#echo "INFO: cleartool mkelem -master -eltype directory -c \"$comment\" $buildID "
	#cleartool mkelem -master -eltype directory -c "$comment" $buildID

	echo "INFO: Creating drop specific directory $buildID"
	mkdir $buildID

	cd $buildID

	#cleartool mkelem -master -eltype directory -c "$comment" sdk
	#cleartool mkelem -master -eltype directory -c "$comment" obsolete
	#cleartool mkelem -master -eltype directory -c "$comment" inp
	echo "INFO: cp $WORK_LOCATION/$kitversion/*"
	cp -rf $WORK_LOCATION/$kitversion/* .
	#find . -type f | xargs -n1 cleartool mkelem -master -c "$comment"
	cd $WRWB_CONTAINER
	#cleartool co -nc Releases.txt
	echo "$buildID	$labeltype " >> Releases.txt	
	echo "--------------------------------------------------------------------"
	echo "found `cleartool lsco -me -r -s | wc -l` checkouts !"
	echo "--------------------------------------------------------------------"
	sleep 2
	echo "--------------------------------------------------------------------"
	echo "checkin: "
	echo "--------------------------------------------------------------------"
#	cleartool lsco -me -r -s | xargs cleartool ci -c "$comment" 
	echo "--------------------------------------------------------------------"
	echo ""
	echo ""
	echo ""
	echo "--------------------------------------------------------------------"
	echo "!!!!   CHECK  CHECK  DOUBLE-CHECK    !!!!!"
	echo ""
	echo ""
	echo "diff -qr $WRWB_CONTAINER/$buildID $WORK_LOCATION/$kitversion/  : "
	diff -qr $WRWB_CONTAINER/$buildID $WORK_LOCATION/$kitversion/
	echo ""
	echo "-------     du -sh *      -------------------------------------------"
	echo ""
	cd $buildID
	du -sh *
	echo ""
	echo "---------------------------------------------------------------------"
	echo ""
	
	echo "Check INP drop!!!!!!"
	echo ""

	echo "Update the delivery file"
	echo ""
	
	#cd $PRODUCT_DIR/mfg_$WBVERSION
	cd $PRODUCT_DIR/mfg_3362
	cleartool co -nc WBDefines.mk
	echo "WRWB_VERSION:=$buildID" > WBDefines.mk
	echo "#notify=rupert.gratz@windriver.com,johann.draschwandtner@windriver.com,martin.gutschelhofer@windriver.com" >> WBDefines.mk
	cleartool ci -c "$comment" WBDefines.mk

	echo "echo WRWB_VERSION:=$buildID"
	echo "Updated the delivery file"
	echo ""

	echo `cleartool pwv`
	echo "nach pwv"

	############ Now checkin the phoenix stuff ###############

	echo "Now checkin the Phoenix drops "

	endview
	
}


checkHost2Build(){
	if [ ! `hostname` = "ala-ccbuild-lx1" ]; then
		echo " Invalid host `hostname`, goto ala-ccbuild-lx1 "
		exit 1	
	fi
}

qfTest(){
	getWBVersion
	setVNC 
	echo "Start time: `date +%r`" 
	sh /folk/wsadm/bin/qftest/wbsanity-MOD.sh /export1/t5-dev/wrwb_store/weeklies/wb$kitversion > /folk/wsadm/release/scripts/qftest.log
	unsetVNC
	echo "End time: `date +%r`" 

	#cleanup reports
	result= `cat /folk/wsadm/release/scripts/qftest.log | grep RV`
	if [ "$result" = "RV: 1" ]; then
		rm -rf /folk/wsadm/bin/logs/logs
		rm -rf /folk/wsadm/bin/logs/reports
		echo "qftest passed"		
		echo "/folk/wsadm/release/scripts/qftest.log"
	else
		echo "qftest failed, check logs"
		echo "/folk/wsadm/release/scripts/qftest.log"
	fi 

}
setVNC(){
	
	which vncserver > /dev/null 2>&1 || error "command vncserver not found"
	display_nr=`vncserver 2>&1 | grep "$HOSTNAME": | grep "New" | head -1 | awk 'BEGIN{FS=":"}{print $2}' | cut -f1 -d" "`
	if [ "$?" != 0 ]; then
		vncserver -geometry 1152x864 2>&1 
		error "Cannot set dispaly."
	else
		DISPLAY_VERIFY="set"
	fi
	echo "Set vncserver: $HOSTNAME:$display_nr" 
	export DISPLAY=$HOSTNAME:$display_nr

}

unsetVNC(){

        if [ "$DISPLAY_VERIFY" = "set" ]; then
                vncserver -kill $HOSTNAME:$display_nr
                if [ "$?" != "0" ]; then
                        echo "INFO: Could not kill vncserver $HOSTNAME:$display_nr"
                fi
        fi

}

echo " "
# write the wanted Information to the weekly_release_status.xml file 

case "$*" in
checkin) 	checkin ;;

setview) 	setview ;;

cdGeneration)	checkHost2Build
		makeCD ;;

endview) 	endview ;;

build)		checkHost2Build
		build ;;

update_build_cspec) update_build_cspec
		;;

findChangeSet)  findChangeSet ;;

label)		label ;;

startWB) 	startWB ;;

findchangeset)	findchangeset;;

echoWBVersion)	getWBVersion;;
echoWBPEVersion)	getWBPEVersion;;
echoCDLocation) echoCDLocation ;;

qfTest) 	qfTest ;;
*)	echo "call: setview label  update_build_cspec build  cdGeneration  startWB checkin endview  echoWBVersion echoCDLocation qfTest findchangeset"
	exit 0
	;;
esac
