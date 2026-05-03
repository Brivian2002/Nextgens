import React from 'react';
import { 
  Shield, 
  Wifi, 
  Lock, 
  Smartphone, 
  Bell, 
  Moon, 
  History,
  Info,
  ChevronRight,
  Database,
  Zap
} from 'lucide-react';
import { cn } from '../lib/utils';

export default function Settings() {
  const settingsSections = [
    {
      title: 'Connectivity',
      items: [
        { id: 'profiles', label: 'VPN Profiles', icon: Shield, detail: '3 profiles active' },
        { id: 'dns', label: 'DNS Configuration', icon: Database, detail: 'Cloudflare (DoH)' },
        { id: 'killswitch', label: 'Kill Switch', icon: Lock, toggle: true, value: true },
        { id: 'autoconnect', label: 'Auto-connect', icon: History, toggle: true, value: false },
      ]
    },
    {
      title: 'Features',
      items: [
        { id: '4glite', label: '4G Lite Mode', icon: Zap, toggle: true, value: true },
        { id: 'hotspot', label: 'Hotspot Auto-share', icon: Wifi, toggle: true, value: false },
        { id: 'root', label: 'Root Privileges', icon: Smartphone, detail: 'Enabled' },
      ]
    },
    {
      title: 'App Preferences',
      items: [
        { id: 'notifications', label: 'Notifications', icon: Bell, toggle: true, value: true },
        { id: 'darkmode', label: 'Dark Mode', icon: Moon, detail: 'System' },
        { id: 'about', label: 'About Nextgens', icon: Info, detail: 'v2.4.0' },
      ]
    }
  ];

  return (
    <div className="space-y-8 pb-8">
      {settingsSections.map((section) => (
        <div key={section.title} className="space-y-3">
          <h3 className="text-[10px] font-black text-gray-400 dark:text-gray-500 uppercase tracking-[0.2em] px-2">
            {section.title}
          </h3>
          <div className="bg-white dark:bg-gray-800 border border-gray-100 dark:border-gray-700 rounded-2xl overflow-hidden shadow-sm">
            {section.items.map((item, idx) => {
              const Icon = item.icon;
              return (
                <div 
                  key={item.id}
                  className={cn(
                    "flex items-center justify-between p-4 cursor-pointer hover:bg-gray-50 dark:hover:bg-gray-700/50 transition-colors",
                    idx !== section.items.length - 1 && "border-b border-gray-50 dark:border-gray-700"
                  )}
                  id={`settings-${item.id}`}
                >
                  <div className="flex items-center gap-3">
                    <div className="w-8 h-8 rounded-lg bg-primary/5 text-primary flex items-center justify-center">
                      <Icon size={18} />
                    </div>
                    <span className="text-sm font-semibold text-gray-700 dark:text-gray-200">{item.label}</span>
                  </div>
                  
                  <div className="flex items-center gap-2">
                    {item.toggle ? (
                      <div className={cn(
                        "w-10 h-5 rounded-full relative transition-colors duration-300",
                        item.value ? "bg-primary" : "bg-gray-200 dark:bg-gray-700"
                      )}>
                        <div className={cn(
                          "absolute top-1 w-3 h-3 bg-white rounded-full transition-all duration-300 shadow",
                          item.value ? "left-6" : "left-1"
                        )} />
                      </div>
                    ) : (
                      <>
                        <span className="text-xs text-gray-400 font-medium">{item.detail}</span>
                        <ChevronRight size={16} className="text-gray-300" />
                      </>
                    )}
                  </div>
                </div>
              );
            })}
          </div>
        </div>
      ))}

      <div className="pt-4 text-center">
        <p className="text-[9px] text-gray-400 font-medium uppercase tracking-widest">
          Designed by NextGens Global Security
        </p>
      </div>
    </div>
  );
}
