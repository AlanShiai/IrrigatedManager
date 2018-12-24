#!/bin/sh
#
# Configure a VIP for Analysis.
# Must be executed in the VIP directory. Please un-comment HW-Dependent stuff as you see fit.
#
# The VIP should have been created with -debug_opt and BUNDLE_STANDALONE_DEVELOPMENT . Example
# vxprj vip create bsp6x_itl_x86coreix_2_1_1_0 diab matxm32_smp_hpc_vip/matxm32_smp_hpc_vip.wpj -debug_opt -profile PROFILE_STANDALONE_DEVELOPMENT -vsb matxm32
#

# Create backup
ORIG_WPJ=`ls *.wpj`
if [ ! -f "$ORIG_WPJ" ]; then
  echo "ERROR: Cannot find $ORIG_WPJ"
  exit 1
elif [ ! -f "${ORIG_WPJ}.orig" ]; then
  cp "${ORIG_WPJ}" "${ORIG_WPJ}.orig"
fi

### Simulator bundles: Use these instead of general ones below on simulator
#vxprj component add INCLUDE_SPY INCLUDE_ANALYSIS_TEST INCLUDE_WVNETD

### general test and analysis components
vxprj bundle add BUNDLE_EDR
vxprj component add INCLUDE_IPTELNETS INCLUDE_SPY INCLUDE_RAM_DISK INCLUDE_DOSFS_FMT INCLUDE_TAR INCLUDE_ANALYSIS_TEST INCLUDE_WVNETD INCLUDE_NFS_MOUNT_ALL INCLUDE_ANALYSIS_AGENT INCLUDE_ANALYSIS_SCRIPT INCLUDE_SYSTEMVIEWER_AGENT INCLUDE_TRIGGERING
vxprj component add INCLUDE_STOP_MODE_AGENT_START INCLUDE_DEBUG_AGENT_START INCLUDE_CORE_DUMP_RTP_COMPRESS_ZLIB INCLUDE_CORE_DUMP_COMPRESS INCLUDE_CORE_DUMP_SHOW
vxprj parameter set EDR_ERRLOG_SIZE "(6 * VM_PAGE_SIZE)"
vxprj parameter set PM_RESERVED_MEM "(32*1024*1024)"
vxprj parameter set CORE_DUMP_REGION_SIZE "(pmFreeSpace(EDR_PM_ARENA) / 2)"
vxprj parameter set KERNEL_APPL_CORE_DUMP_ENABLE TRUE
vxprj parameter set RAM_DISK_SIZE "(16*1024*1024)"
#### After cold boot, use following commands to initialize:
## coreDumpDevFormat(10)
## dosfsDiskFormat "/ram0"
vxprj parameter set CORE_DUMP_RTP_FS_PATH '"/ram0"'

### Kernel Hardening
vxprj component add INCLUDE_KERNEL_HARDENING INCLUDE_MEM_EDR_SHELL_CMD INCLUDE_MEM_EDR_SHOW INCLUDE_MEM_EDR_RTC INCLUDE_MEM_EDR_RTP_SHELL_CMD INCLUDE_MEM_EDR_RTP_SHOW

### === Hardware Dependent: HW perf counters and test ===
### bsp6x_fsl_p2020_rdb
#vxprj component add INCLUDE_HPC_E500_CORE INCLUDE_HPC_FSL_P2020_DEVICE
## vxprj component add INCLUDE_ANALYSIS_HPC_API_TEST_HW INCLUDE_ANALYSIS_HPC_TEST_HW
#vxprj component add INCLUDE_P2020RDB_DTB_VXBOOT INCLUDE_STANDALONE_SYM_TBL
### itl_64_nehalem
#vxprj bundle add BUNDLE_MATXM_CORE_411_WR
#vxprj bundle add BUNDLE_MSB_FAST_REBOOT
#vxprj component add INCLUDE_HPC_I86_COREI7 INCLUDE_HPC_I86_COREI7_UNCORE
# vxprj component add INCLUDE_ANALYSIS_HPC_API_TEST_HW INCLUDE_ANALYSIS_HPC_TEST_HW
#vxprj component add INCLUDE_CPLUS_IOSTREAMS INCLUDE_STANDALONE_SYM_TBL
### itl_x86_coreix
#vxprj component add INCLUDE_HPC_I86_COREI7 INCLUDE_HPC_I86_COREI7_UNCORE
##vxprj component add INCLUDE_ANALYSIS_HPC_TEST_HW INCLUDE_ANALYSIS_HPC_API_TEST_HW
#vxprj component add DRV_VXBEND_DC2114X INCLUDE_CPLUS_IOSTREAMS INCLUDE_STANDALONE_SYM_TBL
### itl_generic
### itl_core2_64 Bearlake
#vxprj bundle add BUNDLE_BEARLAKE
#vxprj bundle add BUNDLE_MSB_FAST_REBOOT
vxprj component remove INCLUDE_MULTI_STAGE_BOOT
vxprj component add INCLUDE_WARM_BOOT
vxprj component add INCLUDE_CPLUS_IOSTREAMS INCLUDE_STANDALONE_SYM_TBL
#vxprj component add INCLUDE_HPC_I86_CORE2
### bsp6x_ti_am335x_evm
#vxprj component add INCLUDE_HPC_ARM_PMU INCLUDE_ANALYSIS_HPC_API_TEST_HW INCLUDE_ANALYSIS_HPC_TEST_HW
#vxprj component add INCLUDE_CPLUS_IOSTREAMS INCLUDE_STANDALONE_SYM_TBL

### Optional add-ons: Avoid if the VIP should be fairly original
#vxprj bundle add BUNDLE_POSIX
#vxprj component add INCLUDE_IPIFCONFIG_CMD INCLUDE_TIMESTAMP64

### From Sebastien, currently unused
#vxprj component add INCLUDE_ANALYSIS_TOP_DEMO
#vxprj component remove INCLUDE_ANALYSIS_AUX_CLOCK
