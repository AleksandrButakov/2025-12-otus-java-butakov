package ru.anbn.hw11.cachehw;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class MyCache<K, V> implements HwCache<K, V> {

    // Потокобезопасность обеспечим через synchronized на методах класса.
    // Используем WeakHashMap для хранения. Тут слабые ключи.
    private final Map<K, V> storage = new WeakHashMap<>();

    // Слушателей храним в WeakReference. Если пользователь забыл вызвать removeListener,
    // GC сам очистит память, и кэш не будет течь (Best Practice для паттерна Observer).
    private final List<WeakReference<HwListener<K, V>>> listeners = new ArrayList<>();

    @Override
    public synchronized void put(K key, V value) {
        storage.put(key, value);
        notifyListeners(key, value, "PUT");
    }

    @Override
    public synchronized void remove(K key) {
        V value = storage.remove(key);
        notifyListeners(key, value, "REMOVE");
    }

    @Override
    public synchronized V get(K key) {
        V value = storage.get(key);
        notifyListeners(key, value, "GET");
        return value;
    }

    @Override
    public synchronized void addListener(HwListener<K, V> listener) {
        if (listener != null) {
            listeners.add(new WeakReference<>(listener));
        }
    }

    @Override
    public synchronized void removeListener(HwListener<K, V> listener) {
        if (listener == null) return;
        listeners.removeIf(ref -> {
            HwListener<K, V> current = ref.get();
            return current == null || current.equals(listener);
        });
    }

    // Метод уведомления слушателей с автоматической чисткой "мертвых" ссылок.
    private void notifyListeners(K key, V value, String action) {
        List<HwListener<K, V>> activeListeners = new ArrayList<>();

        // Очищаем список от отработавших GC ссылок и собираем живые.
        listeners.removeIf(ref -> {
            HwListener<K, V> listener = ref.get();
            if (listener == null) {
                return true; // Ссылка пустая, удаляем из списка.
            }
            activeListeners.add(listener);
            return false;
        });

        // Безопасно оповещаем живых слушателей.
        for (HwListener<K, V> listener : activeListeners) {
            try {
                listener.notify(key, value, action);
            } catch (Exception e) {
                // Ошибка в одном слушателе не должна приводить к падению всего кэша.
                System.err.println("Error notifying cache listener: " + e.getMessage());
            }
        }
    }
}
