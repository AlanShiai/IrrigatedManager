#!/bin/sh

/folk/wb-fast/ashi/windriver/vx20180409170702_vx7-integration/wrenv.sh -p vxworks-7

vxsim -f vxworks-7/samples/prebuilt_projects/vip_vxsim_linux_gnu/default/vxWorks -d simnet_nat -p 4
