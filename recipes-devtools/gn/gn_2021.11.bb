SUMMARY = "A meta-build system that generates build files for Ninja"
DESCRIPTION = "GN can generate Ninja build files for C, C++, Rust, \
Objective C, and Swift source on most popular platforms"
DEPENDS = "ninja-native"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=0fca02217a5d49a14dfe2d11837bb34d"

SRC_URI = "git://gn.googlesource.com/gn;protocol=https;branch=main"
SRCREV = "79c6c1b1a24c46df5a773cc61604bb5051ca6cf4"
UPSTREAM_VERSION_UNKNOWN = "1"

S = "${WORKDIR}/git"


do_configure () {
	python3 build/gen.py --no-strip
}

do_compile () {
	ninja -v ${PARALLEL_MAKE} -C out
	out/gn_unittests
}

do_install () {
	install -D out/gn ${D}${bindir}/gn
}

BBCLASSEXTEND = "native"
