SUMMARY = "System profiling, app tracing and trace analysis"
DESCRIPTION = "Perfetto is a production-grade open-source stack for \
performance instrumentation and trace analysis. It offers services and \
libraries for recording system-level and app-level traces, native + \
java heap profiling, a library for analyzing traces using SQL and a web \
based UI to visualize and explore multi-GB traces."
HOMEPAGE = "https://perfetto.dev"
DEPENDS = "gn-native"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=f87516e0b698007e9e75a1fe1012b390"

SRC_URI = "git://android.googlesource.com/platform/external/perfetto;protocol=https;branch=master \
           file://0001-NOUPSTREAM-Autogenerate-recipe-fragments-from-the-bu.patch \
           file://0002-NOUPSTREAM-Disable-Werror.patch \
           file://0003-pbtxt_to_pb-Add-missing-include.patch \
           "

SRCREV = "e5fc9f62634b179a6ced07fd2f565a9cee70f5bd"
UPSTREAM_VERSION_UNKNOWN = "1"

S = "${WORKDIR}/git"

do_configure () {
	# All indentation here *must* use spaces rather than tabs
	gn gen --args="\
            target_cpu=\"arm64\" \
            target_os=\"linux\" \
            target_sysroot=\"${STAGING_DIR_TARGET}\" \
            is_clang=false \
            is_debug=false \
            skip_buildtools_check=true \
            ar=\"${BUILD_AR}\" \
            cc=\"${BUILD_CC}\" \
            cxx=\"${BUILD_CXX}\" \
            target_ar=\"${AR}\" \
            target_cc=\"${CC}\" \
            target_cxx=\"${CXX}\"
            target_strip=\"${STRIP}\"
            extra_target_cflags=\"${CFLAGS}\"
            extra_target_cxxflags=\"${CXXFLAGS}\"
            extra_target_ldflags=\"${LDFLAGS}\"
            " out/
}

do_compile () {
	ninja -v ${PARALLEL_MAKE} -C out/ tracebox
}

do_install () {
	install -D out/tracebox ${D}${bindir}/tracebox

	# The perfetto ui defaults to /data/misc/perfetto-traces.
	# This convenience directory allows us to copy-and-paste
	# from the UI (and should be removed if/when the UI picks
	# a better default for non-Android systems).
	install -d ${D}/data/misc ${D}/home/root/perfetto-traces
	ln -s /home/root/perfetto-traces ${D}/data/misc/perfetto-traces
}

FILES:${PN} += "/home/root /data/misc"

#
# AUTO-GENERATED DEPENDENCIES
#
# Perfetto has a build system configured to build using a lot of vendored libraries.
# At present no attempt has been to removing the vendoring so we must provide
# the vendored source (without breaking BB_NO_NETWORK=1 builds). Currently the
# route we take to try and hammer this square peg into our round hole is to
# abuse the tables in the upstream sources (which are buried in python scripts)
# and use them to generate additional SRC_URI decribing the vendored libraries.
# So, yes... it sucks, and it results in a 7MB binary. However it does work and it
# wasn't prohibitively expensive to package!
#
# Everything below this point is auto-generated using `tools/generate-build-deps`.
# When updating, delete everything below this point and regenerate them from
# scratch. After that, uncomment the components that are actually required to
# build the target tools.
#


#SRC_URI += "https://storage.googleapis.com/perfetto/libbacktrace-177940370e4a6b2509e92a0aaa9749184e64af43.zip;name=libbacktrace;subdir=git/buildtools"
#SRC_URI[libbacktrace.sha256sum] = "21ac9a4209f7aeef766c482be53a7fa365063c031c7077e2070b491202983b31"
#do_configure:append () {
#	(cd buildtools/ && ln -s libbacktrace-* libbacktrace)
#}

#SRC_URI += "git://android.googlesource.com/platform/external/googletest.git;protocol=https;nobranch=1;name=googletest;destsuffix=git/buildtools/googletest"
#SRCREV_googletest = "3f05f651ae3621db58468153e32016bc1397800b"

SRC_URI += "git://chromium.googlesource.com/external/github.com/google/protobuf.git;protocol=https;nobranch=1;name=protobuf;destsuffix=git/buildtools/protobuf"
SRCREV_protobuf = "6a59a2ad1f61d9696092f79b6d74368b4d7970a3"

SRC_URI += "git://chromium.googlesource.com/external/github.com/llvm/llvm-project/libcxx.git;protocol=https;nobranch=1;name=libcxx;destsuffix=git/buildtools/libcxx"
SRCREV_libcxx = "d9040c75cfea5928c804ab7c235fed06a63f743a"

SRC_URI += "git://chromium.googlesource.com/external/github.com/llvm/llvm-project/libcxxabi.git;protocol=https;nobranch=1;name=libcxxabi;destsuffix=git/buildtools/libcxxabi"
SRCREV_libcxxabi = "196ba1aaa8ac285d94f4ea8d9836390a45360533"

SRC_URI += "git://chromium.googlesource.com/external/github.com/llvm/llvm-project/libunwind.git;protocol=https;nobranch=1;name=libunwind;destsuffix=git/buildtools/libunwind"
SRCREV_libunwind = "d999d54f4bca789543a2eb6c995af2d9b5a1f3ed"

#SRC_URI += "git://chromium.googlesource.com/chromium/llvm-project/compiler-rt/lib/fuzzer.git;protocol=https;nobranch=1;name=libfuzzer;destsuffix=git/buildtools/libfuzzer"
#SRCREV_libfuzzer = "debe7d2d1982e540fbd6bd78604bf001753f9e74"

#SRC_URI += "git://chromium.googlesource.com/external/github.com/google/benchmark.git;protocol=https;nobranch=1;name=benchmark;destsuffix=git/buildtools/benchmark"
#SRCREV_benchmark = "090faecb454fbd6e6e17a75ef8146acb037118d4"

SRC_URI += "https://storage.googleapis.com/perfetto/libbacktrace-177940370e4a6b2509e92a0aaa9749184e64af43.zip;name=libbacktrace;subdir=git/buildtools"
SRC_URI[libbacktrace.sha256sum] = "21ac9a4209f7aeef766c482be53a7fa365063c031c7077e2070b491202983b31"
do_configure:append () {
	(cd buildtools && ln -fs libbacktrace-* libbacktrace)
}

#SRC_URI += "https://storage.googleapis.com/perfetto/sqlite-amalgamation-3350400.zip;name=sqlite;subdir=git/buildtools"
#SRC_URI[sqlite.sha256sum] = "f3bf0df69f5de0675196f4644e05d07dbc698d674dc563a12eff17d5b215cdf5"
#do_configure:append () {
#	(cd buildtools && ln -s sqlite-* sqlite)
#}

#SRC_URI += "git://chromium.googlesource.com/external/github.com/sqlite/sqlite.git;protocol=https;nobranch=1;name=sqlite_src;destsuffix=git/buildtools/sqlite_src"
#SRCREV_sqlite_src = "ee3686eb50c0e3dbb087c9a0976f7e37e1b014ae"

#SRC_URI += "git://chromium.googlesource.com/external/github.com/open-source-parsers/jsoncpp.git;protocol=https;nobranch=1;name=jsoncpp;destsuffix=git/buildtools/jsoncpp"
#SRCREV_jsoncpp = "6aba23f4a8628d599a9ef7fa4811c4ff6e4070e2"

#SRC_URI += "git://android.googlesource.com/platform/system/core.git;protocol=https;nobranch=1;name=android-core;destsuffix=git/buildtools/android-core"
#SRCREV_android-core = "9e6cef7f07d8c11b3ea820938aeb7ff2e9dbaa52"

#SRC_URI += "git://android.googlesource.com/platform/system/unwinding.git;protocol=https;nobranch=1;name=android-unwinding;destsuffix=git/buildtools/android-unwinding"
#SRCREV_android-unwinding = "5150e1292ec6b16e4717e86b9e3cfb855eec18a3"

#SRC_URI += "git://android.googlesource.com/platform/system/logging.git;protocol=https;nobranch=1;name=android-logging;destsuffix=git/buildtools/android-logging"
#SRCREV_android-logging = "7b36b566c9113fc703d68f76e8f40c0c2432481c"

#SRC_URI += "git://android.googlesource.com/platform/system/libbase.git;protocol=https;nobranch=1;name=android-libbase;destsuffix=git/buildtools/android-libbase"
#SRCREV_android-libbase = "78f1c2f83e625bdf66d55b48bdb3a301c20d2fb3"

#SRC_URI += "git://android.googlesource.com/platform/system/libprocinfo.git;protocol=https;nobranch=1;name=android-libprocinfo;destsuffix=git/buildtools/android-libprocinfo"
#SRCREV_android-libprocinfo = "fd214c13ededecae97a3b15b5fccc8925a749a84"

#SRC_URI += "git://android.googlesource.com/platform/external/lzma.git;protocol=https;nobranch=1;name=lzma;destsuffix=git/buildtools/lzma"
#SRCREV_lzma = "7851dce6f4ca17f5caa1c93a4e0a45686b1d56c3"

SRC_URI += "git://android.googlesource.com/platform/external/zlib.git;protocol=https;nobranch=1;name=zlib;destsuffix=git/buildtools/zlib"
SRCREV_zlib = "5c85a2da4c13eda07f69d81a1579a5afddd35f59"

#SRC_URI += "git://android.googlesource.com/platform/bionic.git;protocol=https;nobranch=1;name=bionic;destsuffix=git/buildtools/bionic"
#SRCREV_bionic = "332065d57e734b65f56474d136d22d767e36cbcd"

#SRC_URI += "https://storage.googleapis.com/perfetto/test-data-20210909-141933.zip;name=data;subdir=git/buildtools"
#SRC_URI[data.sha256sum] = "95597358b8d434338815731948cf2bb96e2015b8595f05c37c053c2e11f11408"
#do_configure:append () {
#	(cd test && ln -s data-* data)
#}

#SRC_URI += "git://fuchsia.googlesource.com/third_party/linenoise.git;protocol=https;nobranch=1;name=linenoise;destsuffix=git/buildtools/linenoise"
#SRCREV_linenoise = "c894b9e59f02203dbe4e2be657572cf88c4230c3"

#SRC_URI += "https://storage.googleapis.com/perfetto/bloaty-1.1-b3b829de35babc2fe831b9488ad2e50bca939412-mac.zip;name=bloaty;subdir=git/buildtools"
#SRC_URI[bloaty.sha256sum] = "2d301bd72a20e3f42888c9274ceb4dca76c103608053572322412c2c65ab8cb8"
#do_configure:append () {
#	(cd buildtools && ln -s bloaty-* bloaty)
#}
