This README file contains information on the contents of the meta-perfetto layer.

Please see the corresponding sections below for details.

Patches
=======

Please submit any patches against the meta-perfetto layer as a pull request on
the corresponding github project: https://github.com/daniel-thompson/meta-perfetto .

Adding the meta-perfetto layer to your build
============================================

Run `bitbake-layers add-layer meta-perfetto`

Using perfetto
==============

meta-perfetto is a simple two-recipe layer that packages just enough of perfetto
to allow us to run the data capture tools on the target device. It can be combined
with the web-based user interace at https://ui.perfetto.dev . When recording a new
trace set the target platform to "Linux desktop" and replace `perfetto` with
`tracebox`.

For example:
~~~
tracebox \
  -c - --txt \
  -o /data/misc/perfetto-traces/trace \
<<EOF

buffers: {
    size_kb: 63488
    fill_policy: DISCARD
}
buffers: {
    size_kb: 2048
    fill_policy: DISCARD
}
data_sources: {
    config {
        name: "linux.process_stats"
        target_buffer: 1
        process_stats_config {
            scan_all_processes_on_start: true
        }
    }
}
data_sources: {
    config {
        name: "linux.ftrace"
        ftrace_config {
            ftrace_events: "sched/sched_switch"
            ftrace_events: "power/suspend_resume"
            ftrace_events: "sched/sched_wakeup"
            ftrace_events: "sched/sched_wakeup_new"
            ftrace_events: "sched/sched_waking"
            ftrace_events: "sched/sched_process_exit"
            ftrace_events: "sched/sched_process_free"
            ftrace_events: "task/task_newtask"
            ftrace_events: "task/task_rename"
            ftrace_events: "sched/sched_blocked_reason"
            buffer_size_kb: 2048
            drain_period_ms: 250
            symbolize_ksyms: true
        }
    }
}
duration_ms: 10000

EOF
~~~

Currently only tracebox (the all in one tracing binary, similar to busybox or
toybox) is packaged. This is not intrinsic and patches to enable additional
components and integrate them with the init daemon would be very much welcomed.
