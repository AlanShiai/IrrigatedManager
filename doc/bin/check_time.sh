#! /bin/sh

start_time=`date +%s`

sleep 10

end_time=`date +%s`

duration=`expr $end_time - $start_time`

echo $duration

