package com.almoullim.background_location

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.PluginRegistry
import io.flutter.plugin.common.BinaryMessenger
import android.content.Context

class BackgroundLocationPlugin : FlutterPlugin, ActivityAware {

    companion object {

        /**
        Legacy for v1 embedding
         */
        @JvmStatic  // Añade esta anotación para asegurar accesibilidad desde Java
        fun registerWith(registrar: PluginRegistry.Registrar) {  // Usar referencia completa
            val service = BackgroundLocationService.getInstance()
            service.onAttachedToEngine(registrar.context(), registrar.messenger())
            registrar.addRequestPermissionsResultListener(service)
        }

        const val TAG = "com.almoullim.Log.Tag"
        const val PLUGIN_ID = "com.almoullim.background_location"
    }


    override fun onAttachedToEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        BackgroundLocationService.getInstance().onAttachedToEngine(binding.applicationContext, binding.binaryMessenger)
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        BackgroundLocationService.getInstance().onDetachedFromEngine()
    }

    override fun onAttachedToActivity(binding: ActivityPluginBinding) {
        val service = BackgroundLocationService.getInstance()
        service.setActivity(binding)
        binding.addRequestPermissionsResultListener(service)
    }

    override fun onDetachedFromActivityForConfigChanges() {
        this.onDetachedFromActivity()
    }

    override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
        this.onAttachedToActivity(binding)
    }

    override fun onDetachedFromActivity() {
        BackgroundLocationService.getInstance().setActivity(null)
    }
}
