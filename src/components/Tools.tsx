import React, { useState } from 'react';
import { motion } from 'motion/react';
import { TOOLS, ICON_MAP } from '../constants';
import { Search } from 'lucide-react';
import { cn } from '../lib/utils';

export default function Tools() {
  const [search, setSearch] = useState('');

  const filteredTools = TOOLS.filter(tool => 
    tool.name.toLowerCase().includes(search.toLowerCase()) || 
    tool.description.toLowerCase().includes(search.toLowerCase())
  );

  return (
    <div className="space-y-6">
      <div className="relative">
        <Search className="absolute left-4 top-1/2 -translate-y-1/2 text-gray-400" size={18} />
        <input 
          type="text" 
          placeholder="Search network tools..."
          value={search}
          onChange={(e) => setSearch(e.target.value)}
          className="w-full pl-12 pr-4 py-3 bg-gray-50 dark:bg-gray-800 border-none rounded-xl text-sm focus:ring-2 focus:ring-primary/20 outline-none dark:text-gray-200"
        />
      </div>

      <div className="grid grid-cols-2 gap-4">
        {filteredTools.map((tool, idx) => {
          const Icon = ICON_MAP[tool.icon];
          return (
            <motion.div
              key={tool.id}
              initial={{ opacity: 0, scale: 0.9 }}
              animate={{ opacity: 1, scale: 1 }}
              transition={{ delay: idx * 0.05 }}
              whileHover={{ y: -4 }}
              className="p-4 bg-white dark:bg-gray-800 border border-gray-100 dark:border-gray-700 rounded-2xl shadow-sm hover:shadow-md transition-all cursor-pointer group"
              id={`tool-${tool.id}`}
            >
              <div className="w-10 h-10 rounded-xl bg-primary/5 text-primary flex items-center justify-center mb-3 group-hover:bg-primary group-hover:text-white transition-colors">
                <Icon size={20} />
              </div>
              <h3 className="font-bold text-gray-800 dark:text-gray-100 text-sm mb-1">{tool.name}</h3>
              <p className="text-[11px] text-gray-500 leading-tight line-clamp-2">
                {tool.description}
              </p>
            </motion.div>
          );
        })}
      </div>

      {filteredTools.length === 0 && (
        <div className="text-center py-12">
          <p className="text-gray-400 text-sm italic">No tools found matching "{search}"</p>
        </div>
      )}
    </div>
  );
}
