import { 
  Home, 
  Wrench, 
  Code, 
  Settings as SettingsIcon,
  Wifi,
  Shield,
  Zap,
  Globe,
  Lock,
  Terminal,
  Activity,
  Cpu,
  Smartphone,
  Search,
  ChevronRight,
  Power
} from 'lucide-react';

export const TOOLS = [
  { id: 'adblock', name: 'Ad-blocker', description: 'Block intrusive ads globally', icon: 'Shield' },
  { id: 'firewall', name: 'Firewall', description: 'Per-app network control', icon: 'Lock' },
  { id: 'speedtest', name: 'Speed Test', description: 'Benchmark connection', icon: 'Zap' },
  { id: 'wifi', name: 'Wi-Fi Analyzer', description: 'Channel & signal scan', icon: 'Wifi' },
  { id: 'packet', name: 'Packet Capture', description: 'Analyze raw traffic', icon: 'Activity' },
  { id: 'ssh', name: 'SSH Tunnel', description: 'Secure remote access', icon: 'Terminal' },
  { id: 'dns', name: 'DNS Analytics', description: 'Detailed DNS lookup logs', icon: 'Search' },
  { id: 'usage', name: 'Data Usage', description: 'Monitor app consumption', icon: 'Cpu' },
];

export const ICON_MAP: Record<string, any> = {
  Home,
  Build: Wrench,
  Code,
  Settings: SettingsIcon,
  Wifi,
  Shield,
  Zap,
  Globe,
  Lock,
  Terminal,
  Activity,
  Cpu,
  Smartphone,
  Search,
  ChevronRight,
  Power
};
