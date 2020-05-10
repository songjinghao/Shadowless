package com.paul.song.analytics.plugin

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class ShadowlessPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        println("ShadowlessPlugin start!")

        AppExtension appExtension = project.extensions.findByType(AppExtension.class)
        appExtension.registerTransform(new ShadowlessTransform(project))
    }
}
