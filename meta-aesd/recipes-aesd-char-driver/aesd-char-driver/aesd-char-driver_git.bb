# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.




LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://../LICENSE;md5=8ed1a118f474eea5e159b560c339329b"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI = "git://github.com/cu-ecen-aeld/assignment-1-part-of-the-crew.git;protocol=https;branch=master"
SRC_URI += "file://aesdchar_init"

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "711481e6f268934da82b3a4ac1cb91ecb178aa0d"

S = "${WORKDIR}/git/aesd-char-driver"



inherit module

EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}/aesd-char-driver"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"


inherit update-rc.d
INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "aesdchar_init"
INITSCRIPT_PARAMS = "defaults 90 10"

FILES:${PN} += "${sysconfdir}/*"

do_configure () {
        :
}

do_compile () {
        oe_runmake
}


KERNEL_VERSION = "5.15.124-yocto-standard"


do_install () {
        # TODO: Install your binaries/scripts here.
        # Be sure to install the target directory with install -d first
        # Yocto variables ${D} and ${S} are useful here, which you can read about at 
        # https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-D
        # and
        # https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-S
        # See example at https://github.com/cu-ecen-aeld/ecen5013-yocto/blob/ecen5013-hello-world/meta-ecen5013/recipes-ecen5013/ecen5013-hello-world/ecen5013-hello-world_git.bb
        install -d ${D}${sysconfdir}/init.d
        install -m 0755 ${WORKDIR}/aesdchar_init ${D}${sysconfdir}/init.d

        install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/
        install -m 0755 ${S}/aesdchar.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/
}
